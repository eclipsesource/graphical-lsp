package com.eclipsesource.glsp.ecore;

import com.eclipsesource.glsp.api.action.kind.RequestOperationsAction;
import com.eclipsesource.glsp.api.operations.Group;
import com.eclipsesource.glsp.api.operations.IOperationConfiguration;
import com.eclipsesource.glsp.api.operations.Operation;
import com.eclipsesource.glsp.ecore.model.ModelTypes;

public class EcoreOperationConfiguration implements IOperationConfiguration {

	@Override
	public Operation[] getOperations(RequestOperationsAction action) {
		Group classifierGroup = new Group("ecore.classifier", "Classifier");
		Group classGroup = new Group("ecore.class", "Class", classifierGroup);
		Operation createEClass = new Operation("Class", ModelTypes.ECLASS, Operation.Kind.CREATE_NODE, classGroup);
		Operation createAbstract = new Operation("Abstract", ModelTypes.ABSTRACT, Operation.Kind.CREATE_NODE,
				classGroup);
		Operation createInterface = new Operation("Interface", ModelTypes.INTERFACE, Operation.Kind.CREATE_NODE,
				classGroup);
		Operation createEnum = new Operation("Enum", ModelTypes.ENUM, Operation.Kind.CREATE_NODE, classifierGroup);
		Operation createDataType = new Operation("DataType", ModelTypes.DATATYPE, Operation.Kind.CREATE_NODE, classifierGroup);
		Group relationGroup = new Group("ecore.relation", "Relation");
		Operation createEcoreEdge = new Operation("Reference", ModelTypes.REFERENCE, Operation.Kind.CREATE_CONNECTION,
				relationGroup);
		Operation createComposition = new Operation("Composition", ModelTypes.COMPOSITION,
				Operation.Kind.CREATE_CONNECTION, relationGroup);
		Operation createInheritance = new Operation("Inheritance", ModelTypes.INHERITANCE,
				Operation.Kind.CREATE_CONNECTION, relationGroup);

		Group featureGroup = new Group("ecore.feature", "Feature");
		Operation createAttributeOperation = new Operation("Attribute", ModelTypes.ATTRIBUTE,
				Operation.Kind.CREATE_NODE, featureGroup);
		Operation createEnumLiteral = new Operation("Literal", ModelTypes.ENUMLITERAL, Operation.Kind.CREATE_NODE,
				featureGroup);
		Operation[] operations = { createEClass, createAbstract, createInterface, createEnum,createDataType, createAttributeOperation,
				createEnumLiteral, createEcoreEdge, createComposition, createInheritance };
		return operations;
	}

}
