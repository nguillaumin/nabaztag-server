package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyNathanConfigForm;
import net.violet.platform.datamodel.NathanTag;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.NathanTagData;
import net.violet.platform.interactif.config.NathanPlayerConfig;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyNathanConfigAction extends DispatchActionForLoggedUserWithObject {

	// prints the page

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject object = SessionTools.getRabbitFromSession(session);

		final MyNathanConfigForm myForm = (MyNathanConfigForm) form;

		myForm.setSexTags(NathanTagData.findTagsByCategory(0));
		myForm.setInterpretationTags(NathanTagData.findTagsByCategory(1));
		myForm.setEffectsTags(NathanTagData.findTagsByCategory(2));
		myForm.setAccentTags(NathanTagData.findTagsByCategory(3));

		final long versionId = Long.parseLong(myForm.getVersionId());
		final NathanVersion theVersion = Factories.NATHAN_VERSION.find(versionId);

		// checks if the version exists and the user really is the version's
		// author
		if ((theVersion != null) && theVersion.getAuthor().equals(object)) {
			for (final NathanTag tag : theVersion.getTags()) {
				if (tag.getCateg() == 0) {
					myForm.setCheckedSexTag(tag.getId());
				}
				if (tag.getCateg() == 1) {
					myForm.setCheckedInterpretationTag(tag.getId());
				}
				if (tag.getCateg() == 2) {
					myForm.setCheckedEffectsTag(tag.getId());
				}
				if (tag.getCateg() == 3) {
					myForm.setCheckedAccentTag(tag.getId());
				}
			}

			myForm.setDescription(StringShop.EMPTY_STRING);
			if (theVersion.getDescription() != null) {
				myForm.setDescription(theVersion.getDescription());
			}

			if (theVersion.getShared()) {
				myForm.setIsShared(1);
			} else {
				myForm.setIsShared(0);
			}
		}

		final Long isbn = Long.parseLong(myForm.getIsbn());
		if (isbn != null) {
			myForm.setDicoRoot(NathanPlayerConfig.BOOKSROOT.get(isbn));
		} else {
			myForm.setDicoRoot(StringShop.EMPTY_STRING);
		}

		return mapping.getInputForward();
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject object = SessionTools.getRabbitFromSession(session);

		final MyNathanConfigForm myForm = (MyNathanConfigForm) form;

		final Long versionId = Long.parseLong(myForm.getVersionId());

		String description = myForm.getDescription();
		if (description == null) {
			description = StringShop.EMPTY_STRING;
		}

		final boolean isShared = myForm.getIsShared() == 1;

		final List<NathanTag> checkedTags = new ArrayList<NathanTag>();
		if (Factories.NATHAN_TAG.find(myForm.getCheckedSexTag()) != null) {
			checkedTags.add(Factories.NATHAN_TAG.find(myForm.getCheckedSexTag()));
		}
		if (Factories.NATHAN_TAG.find(myForm.getCheckedInterpretationTag()) != null) {
			checkedTags.add(Factories.NATHAN_TAG.find(myForm.getCheckedInterpretationTag()));
		}
		if (Factories.NATHAN_TAG.find(myForm.getCheckedAccentTag()) != null) {
			checkedTags.add(Factories.NATHAN_TAG.find(myForm.getCheckedAccentTag()));
		}
		if (Factories.NATHAN_TAG.find(myForm.getCheckedEffectsTag()) != null) {
			checkedTags.add(Factories.NATHAN_TAG.find(myForm.getCheckedEffectsTag()));
		}

		final NathanVersion theVersion = Factories.NATHAN_VERSION.find(versionId);

		if ((theVersion != null) && theVersion.getAuthor().equals(object)) {
			NathanVersion.Status newStatus = NathanVersion.Status.FINISHED;
			if (theVersion.getStatus().equals(NathanVersion.Status.AUTHORIZED.toString())) {
				newStatus = NathanVersion.Status.AUTHORIZED;
			}
			if (theVersion.getStatus().equals(NathanVersion.Status.REFUSED.toString())) {
				newStatus = NathanVersion.Status.REFUSED;
			}
			theVersion.setVersionInformation(description, newStatus, isShared, checkedTags);
		}

		request.setAttribute("isbn", myForm.getIsbn());
		request.setAttribute("appletId", myForm.getAppletId());
		return mapping.findForward("goToVersions");
	}

}
