package com.eclipsesource.glsp.ecore.model;

import static com.eclipsesource.glsp.ecore.util.SModelIdUtils.toAttributeLabelId;
import static com.eclipsesource.glsp.ecore.util.SModelIdUtils.toAttributeNodeId;
import static com.eclipsesource.glsp.ecore.util.SModelIdUtils.toClassNodeId;
import static com.eclipsesource.glsp.ecore.util.SModelIdUtils.toInheritanceEdgeId;
import static com.eclipsesource.glsp.ecore.util.SModelIdUtils.toLiteralLabelId;
import static com.eclipsesource.glsp.ecore.util.SModelIdUtils.toLiteralNodeId;
import static com.eclipsesource.glsp.ecore.util.SModelIdUtils.toReferenceEdgeId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sprotty.EdgePlacement;
import org.eclipse.sprotty.EdgePlacement.Side;
import org.eclipse.sprotty.LayoutOptions;
import org.eclipse.sprotty.Point;
import org.eclipse.sprotty.SCompartment;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SLabel;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SNode;

import com.google.inject.Singleton;

@Singleton
public class EcoreSModelConverter {

	private static EdgePlacement centralTop() {
		EdgePlacement placement = new EdgePlacement();
		placement.setSide(Side.top);
		placement.setPosition(0.5d);
		placement.setOffset(0.1d);
		placement.setRotate(false);
		return placement;
	}

	public SEdge createReferenceEdge(EReference eReference) {
		SEdge reference = new SEdge();
		reference.setCssClasses(new ArrayList<>());
		reference.getCssClasses().add("ecore-edge");
		reference.setId(toReferenceEdgeId(eReference));
		if (eReference.isContainment()) {
			reference.getCssClasses().add("composition");
			reference.setType(ModelTypes.COMPOSITION);
		}

		reference.setSourceId(toClassNodeId(eReference.getEContainingClass()));
		reference.setTargetId(toClassNodeId(eReference.getEReferenceType()));
		SLabel refName = new SLabel();
		refName.setId(reference.getId() + "_name");
		refName.setType("label:text");
		refName.setText(String.format("[%s..%s] %s", eReference.getLowerBound(),
				eReference.getUpperBound() == -1 ? "*" : eReference.getUpperBound(),eReference.getName()));
//		refName.setEdgePlacement(centralTop());
		reference.setChildren(new ArrayList<>(Arrays.asList(refName)));

		return reference;
	}

	public SEdge createInheritanceEdge(EClass baseClass, EClass superClass) {
		SEdge reference = new SEdge();
		reference.setCssClasses(new ArrayList<>());
		reference.getCssClasses().add("ecore-edge");
		reference.getCssClasses().add("inheritance");
		reference.setId(toInheritanceEdgeId(baseClass, superClass));
		reference.setType(ModelTypes.INHERITANCE);
		reference.setSourceId(toClassNodeId(baseClass));
		reference.setTargetId(toClassNodeId(superClass));
		return reference;
	}

	public SNode createAttributeLabel(EAttribute eAttribute) {
		SNode attributeNode = new SNode();
		attributeNode.setType(ModelTypes.ATTRIBUTE);
		attributeNode.setId(toAttributeNodeId(eAttribute));
		List<SModelElement> children = new ArrayList<>();
		SLabel attributeLabel = new SLabel();
		attributeLabel.setType("label:text");
		attributeLabel.setId(toAttributeLabelId(eAttribute));
		attributeLabel
				.setText(String.format(" - %s : %s", eAttribute.getName(), eAttribute.getEAttributeType().getName()));
		children.add(attributeLabel);
		attributeNode.setChildren(children);
		return attributeNode;
	}

	public SNode createEnumLiteralLabel(EEnumLiteral eliteral) {
		SNode literalNode = new SNode();
		literalNode.setType(ModelTypes.ENUMLITERAL);
		literalNode.setId(toLiteralNodeId(eliteral));
		List<SModelElement> children = new ArrayList<>();
		SLabel literalLabel = new SLabel();
		literalLabel.setType("label:text");
		literalLabel.setId(toLiteralLabelId(eliteral));
		literalLabel.setType(ModelTypes.ENUMLITERAL);
		literalLabel.setText(" - " + eliteral.getLiteral());
		children.add(literalLabel);
		literalNode.setChildren(children);
		return literalNode;
	}

	public ClassNode createClassNode(EClassifier eClassifier, boolean foreignPackage, Optional<Point> point) {
		ClassNode classNode = new ClassNode();
		classNode.setId(toClassNodeId(eClassifier));
		classNode.setLayout("vbox");
		classNode.setExpanded(true);
		classNode.getCssClasses().add("ecore-node");
		if (foreignPackage) {
			classNode.getCssClasses().add("foreign-package");
		}
		List<SModelElement> classChildren = new ArrayList<SModelElement>();
		classNode.setChildren(classChildren);
		Point location = point.isPresent() ? point.get() : new Point(0, 0);
		classNode.setPosition(location);

		// header
		SCompartment header = new SCompartment();
		header.setId(classNode.getId() + "_header");
		header.setType("comp:header");
		header.setLayout("hbox");
		List<SModelElement> header_children = new ArrayList<SModelElement>();
		header.setChildren(header_children);
		header.setPosition(new Point(0, 0));
		// icon with label
		Icon icon = new Icon();
		icon.setId(classNode.getId() + "_icon");
		icon.setType("icon");
		icon.setLayout("stack");
		LayoutOptions iconLO = new LayoutOptions();
		iconLO.setHAlign("center");
		iconLO.setResizeContainer(false);
		icon.setLayoutOptions(iconLO);

		SLabel iconLabel = new SLabel();
		iconLabel.setId(classNode.getId() + "_iconlabel");
		iconLabel.setType("label:icon");
		String iconLabelText = "C";
		if (eClassifier instanceof EClass) {
			EClass clazz = (EClass) eClassifier;
			if (clazz.isAbstract()) {
				iconLabelText = "A";
				classNode.getCssClasses().add("abstract");

			} else if (clazz.isInterface()) {
				iconLabelText = "I";
				classNode.getCssClasses().add("interface");
			}

		} else if (eClassifier instanceof EEnum) {
			iconLabelText = "E";
			classNode.getCssClasses().add("enum");
		} else if (eClassifier instanceof EDataType) {
			iconLabelText = "D";
			classNode.getCssClasses().add("datatype");
		}
		iconLabel.setText(iconLabelText);
		icon.setChildren(new ArrayList<>(Arrays.asList(iconLabel)));
		header_children.add(icon);

		// label
		SLabel label = new SLabel();
		label.setId(classNode.getId() + "_classname");
		label.setType("label:heading");
		label.setText(eClassifier.getName());
		LayoutOptions labelLO = new LayoutOptions();
		labelLO.setHAlign("center");
		labelLO.setResizeContainer(false);
		label.setLayoutOptions(labelLO);
		header_children.add(label);
		classChildren.add(header);

		if (eClassifier instanceof EClass) {
			SCompartment attributeCompartment = new SCompartment();
			attributeCompartment.setId(classNode.getId() + "_attrs");
			attributeCompartment.setType("comp:comp");
			attributeCompartment.setLayout("vbox");
			attributeCompartment.setPosition(new Point(0, 0));
			List<SModelElement> attributes = new ArrayList<SModelElement>();
			LayoutOptions attributeCompartementLO = new LayoutOptions();
			attributeCompartementLO.setHAlign("left");
			attributeCompartment.setLayoutOptions(attributeCompartementLO);
			attributeCompartment.setChildren(attributes);
			classChildren.add(attributeCompartment);
		} else if (eClassifier instanceof EEnum) {
			classNode.setType(ModelTypes.ENUM);
			SCompartment literalsCompartment = new SCompartment();
			LayoutOptions literalsCompartmentLO = new LayoutOptions();
			literalsCompartmentLO.setHAlign("left");
			literalsCompartment.setLayoutOptions(literalsCompartmentLO);
			literalsCompartment.setId(classNode.getId() + "_enums");
			literalsCompartment.setType("comp:comp");
			literalsCompartment.setLayout("vbox");
			List<SModelElement> literals = new ArrayList<SModelElement>();
			literalsCompartment.setChildren(literals);
			classChildren.add(literalsCompartment);
		} else if (eClassifier instanceof EDataType) {
			SCompartment detailsComparment = new SCompartment();
			detailsComparment.setId(classNode.getId() + "_details");
			detailsComparment.setType("comp:comp");
			detailsComparment.setLayout("vbox");
			detailsComparment.setPosition(new Point(0, 0));
			List<SModelElement> details = new ArrayList<SModelElement>();
			LayoutOptions attributeCompartementLO = new LayoutOptions();
			attributeCompartementLO.setHAlign("left");
			attributeCompartementLO.setVAlign("center");
			detailsComparment.setLayoutOptions(attributeCompartementLO);
			SLabel detailLabel = new SLabel();
			detailLabel.setText(((EDataType) eClassifier).getInstanceClassName());
			detailLabel.setType("label:text");
			details.add(detailLabel);
			detailsComparment.setChildren(details);

			classChildren.add(detailsComparment);
		}

		return classNode;
	}
}
