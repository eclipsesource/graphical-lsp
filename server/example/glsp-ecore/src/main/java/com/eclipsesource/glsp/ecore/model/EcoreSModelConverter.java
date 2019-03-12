package com.eclipsesource.glsp.ecore.model;

import static com.eclipsesource.glsp.ecore.util.SModelIdUtils.toAttributeLabelId;
import static com.eclipsesource.glsp.ecore.util.SModelIdUtils.toClassNodeId;
import static com.eclipsesource.glsp.ecore.util.SModelIdUtils.toInheritanceEdgeId;
import static com.eclipsesource.glsp.ecore.util.SModelIdUtils.toLiteralLabelId;
import static com.eclipsesource.glsp.ecore.util.SModelIdUtils.toReferenceEdgeId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sprotty.EdgePlacement;
import org.eclipse.sprotty.EdgePlacement.Side;
import org.eclipse.sprotty.LayoutOptions;
import org.eclipse.sprotty.Point;
import org.eclipse.sprotty.SCompartment;
import org.eclipse.sprotty.SLabel;
import org.eclipse.sprotty.SModelElement;

import com.google.inject.Singleton;

@Singleton
public class EcoreSModelConverter {

	private static EdgePlacement centralTop() {
		EdgePlacement placement= new EdgePlacement();
		placement.setSide(Side.top);
		placement.setPosition(0.5d);
		return placement;
	}
	public EcoreEdge createReferenceEdge(EReference eReference) {
		EcoreEdge reference = new EcoreEdge();
		reference.getCssClasses().add("ecore-edge");
		reference.setId(toReferenceEdgeId(eReference));
		if (eReference.isContainment()) {
			reference.getCssClasses().add("composition");
			reference.setType(ModelTypes.COMPOSITION);
		}

		reference.setSourceId(toClassNodeId(eReference.getEContainingClass()));
		reference.setTargetId(toClassNodeId(eReference.getEReferenceType()));
		reference.setMultiplicitySource("0..1");
		reference.setMultiplicityTarget(String.format("%s..%s", eReference.getLowerBound(),
				eReference.getUpperBound() == -1 ? "*" : eReference.getUpperBound()));
		SLabel refName = new SLabel();
		refName.setId(reference.getId() + "_name");
		refName.setType("label:text");
		refName.setText(eReference.getName());
		refName.setEdgePlacement(centralTop());
		reference.setChildren(new ArrayList<>(Arrays.asList(refName)));
		
		return reference;
	}

	public EcoreEdge createInheritanceEdge(EClass baseClass, EClass superClass) {
		EcoreEdge reference = new EcoreEdge();
		reference.getCssClasses().add("ecore-edge");
		reference.getCssClasses().add("inheritance");
		reference.setId(toInheritanceEdgeId(baseClass, superClass));
		reference.setType(ModelTypes.INHERITANCE);
		reference.setSourceId(toClassNodeId(baseClass));
		reference.setTargetId(toClassNodeId(superClass));
		return reference;
	}

	public SLabel createPropertyLabel(EAttribute eAttribute) {
		SLabel attribute = new SLabel();
		attribute.setId(toAttributeLabelId(eAttribute));
		attribute.setType(ModelTypes.ATTRIBUTE);
		attribute.setText(String.format(" - %s : %s", eAttribute.getName(), eAttribute.getEAttributeType().getName()));
		return attribute;
	}
	
	public SLabel createEnumLiteralLabel(EEnumLiteral eliteral) {
		SLabel literal = new SLabel();
		literal.setId(toLiteralLabelId(eliteral));
		literal.setType(ModelTypes.ENUMLITERAL);
		literal.setText(" - " + eliteral.getLiteral());
		return literal;
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
		Point location= point.isPresent()?point.get():new Point(0,0);
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
		}

		return classNode;
	}
}
