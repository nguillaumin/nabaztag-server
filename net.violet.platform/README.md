# Platform

## Description

Platform for rabbit connection and interaction.

## Pre-requisites

### Spread

You can run locally an instance of the Spread messaging daemon. Use `apt-get install spread` on Ubuntu to install it, however you'll also need to:

* Edit `/etc/defaults/spread` and set `ENABLED=1`
* Edit `/etc/init.d/spread` and fix the path to the `spread.conf` file (See [this bug](https://bugs.launchpad.net/ubuntu/+source/spread/+bug/191849)).

Apparently Spread can be disabled when setting the `silence` system property (`-Dsilence=true`).

### Jabber

You need to run a local XMPP server so that the webapp and the rabbit can connect to it. The adress of the machine running the server must be set in `constante.properties` (`XMPP_NABAZTAG_DOMAIN`) so that it's correctly given to the rabbit. I believe Violet ran ejabberd, but I was able to also use OpenFire successfully.

#### Nabaztag side

The rabbit will try to register itself if it cannot log in, so your server must have in-band registration enabled (XEP-0077). The original bootcode of the rabbit used a custom namespace for registration `violet:iq:register` but the XMPP packets were the same as the now standard `jabber:iq:register` (I believe the standard was a draft when Violet developed the XMPP layer). I've modified the bootcode so that is uses the correct namespace now (It's provided under `src/main/resources/nas/rsc/byte_code`).

Once registered the rabbit will try to authenticate over SASL using the DIGEST-MD5 mechanism, so make sure your server is correctly configured.

The main issue is that the rabbit either doesn't fully respect the XMPP protocol, or uses custom extensions:

* It doesn't send a `<presence />` packet just after resouce binding, and OpenFire doesn't like it.
* It first binds a `/boot` XMPP resource, then try to re-bind under `/idle`, which is not standard (AFAIK resource re-binding is not really covered by the XMPP specification). One would need to develop a custom extension, or even modify the core of an existing XMPP server to support that.

The other possibility is to modify the bootcode to comply to the standards. While I managed to modify it so that it sends a `<presence />` packet after being logged in as the `/boot` resource, other modifications looks quite complicated to achieve since changing resource means reconnecting to the XMPP server.

#### Server side

The server side connects to the XMPP server in two ways:

* The `platform` webapp connects a XMPP component (XEP-0114) when it starts up (See the misnamed `net.violet.platform.management.ShutdownHook` class). I'm not sure what that's for.
* A separate XMPP client must be launched manually. This client will log in as `net.violet.platform@hostname` and will be the "user" to which the rabbit will send packets. This is done by running the `net.violet.platform.xmpp.JabberPlatformManagerPacket` class with the following arguments: `sources int rfid asr ptt itmode`.

#### Initial XMPP dialog

Here's what I was able to understand for now:

1. The rabbit connects as `<MAC addr>@server`
2. The rabbit binds the `/boot` resource
3. The rabbit sends an `<iq />` packet containing a request for "sources" to `net.violet.platform@server`
4. The server answers with Base64 encoded data that contains the initial state of the rabbit: Position of the ears, if the nose LED should be blinking because the inbox contains unread messages, if the other LEDs should be blinking to indicate weather forecast, etc.
5. The rabbit then tries to bind a different resource, depending of its state (asleep, awake, etc), like `/idle` (awake). 

This re-bind is unfortunately not standard and will fail with any XMPP server. That means that the rabbit will be stuck in the `/boot` resource and will never receive the packets the platform will send to the `/idle` resource (Messages, feeds, whatever...).

### TODO

* Google Maps is used to lookup GPS coordinates and the API key is hardcoded in the class `net.violet.platform.geocoding.GeoCoder`
  * At least put it in a property file
  * If possible use an embedded system instead of relying on a 3rd party service (MaxMind ?)

* Some tests rely on a specific GMail account with the account login and password hardcoded.

* Some tests rely on 3rd services, like specific podcasts. Ideally they should rely only on local data.
  * A Jetty server could be run during the tests to serve that kind of services.
  * I replaced the existing URLs (pointing to internal servers) to public URLs, not ideal but should work in the meantime.
  * Affects `net.violet.platform.httpclient.DownloadTaskTest`, `net.violet.platform.applications.VActionFullHandlerTest`, `net.violet.platform.feeds.processors.DownloadingProcessorTest`, `net.violet.platform.feeds.crawlers.PodcastFreeCrawlerTest`, `net.violet.platform.daemons.crawlers.processor.downloader.DownloaderTest`, `net.violet.platform.daemons.crawlers.processor.downloader.DownloadJobProcessorTest`

* The system relies on a TTS engine by [Nuance](http://www.nuance.com/), obviously it needs to be replaced by an open-source solution.
  * It can probably be faked for now, by always returning the same audio file
