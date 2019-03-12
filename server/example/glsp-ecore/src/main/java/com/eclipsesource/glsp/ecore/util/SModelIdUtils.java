package com.eclipsesource.glsp.ecore.util;

import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class SModelIdUtils {

	public static Optional<String> toSModelId(EObject eObject) {
		String id = null;
		if (eObject instanceof EClassifier) {
			id = toClassNodeId((EClassifier) eObject);
		} else if (eObject instanceof EReference) {
			id = toReferenceEdgeId((EReference) eObject);
		} else if (eObject instanceof EAttribute) {
			id = toAttributeLabelId((EAttribute) eObject);
		} else if (eObject instanceof EEnumLiteral) {
			id = toLiteralLabelId((EEnumLiteral) eObject);
		}
		return Optional.ofNullable(id);
	}

	public static String toClassNodeId(EClassifier eClassifier) {
		return eClassifier.getEPackage().getName() + "/" + eClassifier.getName();
	}

	public static String toReferenceEdgeId(EReference eReference) {
		return String.format("%s_%s_%s", ((ENamedElement) eReference.eContainer()).getName(),
				eReference.getEReferenceType().getName(), eReference.getName());
	}

	public static String toInheritanceEdgeId(EClass baseClass, EClass superClass) {
		return String.format("%s_%s_parent", baseClass.getName(), superClass.getName());
	}

	public static String toAttributeLabelId(EAttribute attribute) {
		return toClassNodeId(attribute.getEContainingClass()) + "_" + attribute.getName();

	}

	public static String toLiteralLabelId(EEnumLiteral literal) {
		return toClassNodeId(literal.getEEnum()) + "_" + literal.getName();
	}

}
