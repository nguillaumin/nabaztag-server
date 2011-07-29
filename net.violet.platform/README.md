# My Nabaztag

## Description

Platform for rabbit connection and interaction.

## Pre-requisites

### Spread

You must run locally an instance of the Spread messaging daemon. Use `apt-get install spread` on Ubuntu to install it, however you'll also need to:
* Edit `/etc/defaults/spread` and set `ENABLED=1`
* Edit `/etc/init.d/spread` and fix the path to the `spread.conf` file (See [this bug](https://bugs.launchpad.net/ubuntu/+source/spread/+bug/191849)).

### TODO

* Google Maps is used to lookup GPS coordinates and the API key is hardcoded in the class `net.violet.platform.geocoding.GeoCoder`
** At least put it in a property file
** If possible use an embedded system instead of relying on a 3rd party service (MaxMind ?)

* Some tests rely on a specific GMail account with the account login and password hardcoded.

* Some tests rely on 3rd services, like specific podcasts. Ideally they should rely only on local data.
** A Jetty server could be run during the tests to serve that kind of services.
** I replaced the existing URLs (pointing to internal servers) to public URLs, not ideal but should work in the meantime.
** Affects `net.violet.platform.httpclient.DownloadTaskTest`, `net.violet.platform.applications.VActionFullHandlerTest`, `net.violet.platform.feeds.processors.DownloadingProcessorTest`, `net.violet.platform.feeds.crawlers.PodcastFreeCrawlerTest`, `net.violet.platform.daemons.crawlers.processor.downloader.DownloaderTest`, `net.violet.platform.daemons.crawlers.processor.downloader.DownloadJobProcessorTest`

* The system relies on a TTS engine by [Nuance](http://www.nuance.com/), obviously it needs to be replaced by an open-source solution.
** It can probably be faked for now, by always returning the same audio file
