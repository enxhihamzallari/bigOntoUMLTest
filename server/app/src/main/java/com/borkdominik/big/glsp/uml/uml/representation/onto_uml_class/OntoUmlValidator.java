package com.borkdominik.big.glsp.uml.uml.representation.onto_uml_class;

import com.borkdominik.big.glsp.server.core.model.BGEMFModelState;
import com.google.inject.Inject;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.server.features.validation.Marker;
import org.eclipse.glsp.server.features.validation.MarkerKind;
import org.eclipse.glsp.server.features.validation.ModelValidator;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Class;

import java.util.ArrayList;
import java.util.List;

public class OntoUmlValidator implements ModelValidator {

    public static final String OBJECT_CLASS = "Object Class";
    public static final String SUBSTANCE_SORTAL = "Substance Sortal";
    public static final String SORTAL_CLASS = "Sortal Class";
    public static final String RIGID_SORTAL_CLASS = "Rigid Sortal Class";
    public static final String MIXIN_CLASS = "Mixin Class";
    public static final String CATEGORY = "Category";
    public static final String MIXIN = "Mixin";
    public static final String RELATOR = "Relator";
    public static final String ROLE = "Role";
    public static final String ROLE_MIXIN = "RoleMixin";
    public static final String QUALIFIED_MEDIATION = "BigOntoUml::OntoUML::Mediation";
    public static final String MODE = "Mode";
    public static final String STEREOTYPE = "Stereotype";
    public static final String MATERIAL_ASSOCIATION = "MaterialAssociation";
    public static final String DERIVATION = "Derivation";
    public static final String DIRECTED_BINARY_ASSOCIATION = "DirectedBinaryAssociation";
    public static final String MEDIATION = "Mediation";
    public static final String CHARACTERIZATION = "Characterization";
    public static final String QUALIFIED_CHARACTERIZATION = "BigOntoUml::OntoUML::Characterization";
    @Inject
    protected BGEMFModelState modelState;

    @Override
    public List<Marker> validate(List<GModelElement> elements, String reason) {
        return validate(elements.toArray(GModelElement[]::new));
    }

    @Override
    public List<Marker> validate(final GModelElement... elements) {
        var markers = new ArrayList<Marker>();
        for (GModelElement element : elements) {
            if (element instanceof GNode) {
                modelState.getElementIndex().getSemantic(element.getId()).ifPresent(semantic ->
                        {
                            if (semantic instanceof Class c) validateClass((GNode) element, c, markers);
                            if (semantic instanceof Generalization g) validateGeneralisation((GNode) element, g, markers);
                        }
                );
            }
            if (element instanceof GEdge) {
                modelState.getElementIndex().getSemantic(element.getId()).ifPresent(semantic -> {
                            if (semantic instanceof Association a) validateAssociation((GEdge) element, a, markers);
                        }
                );
            }
            element.getChildren().forEach(child -> markers.addAll(validate(child)));
        }

        return markers;
    }

    private void validateClass(GNode element, Class _class, ArrayList<Marker> markers) {
        if (_class.getAppliedStereotypes().isEmpty()) {
            addMarker(element, markers, formatClassMessage("This class has no Stereotype", _class));
            return;
        }

        var stereotype = _class.getAppliedStereotypes().get(0);

        evaluateObjectClass(element, _class, markers, stereotype);
        evaluateSortalClass(element, _class, markers, stereotype);
        evaluateSubstanceSortal(element, _class, markers, stereotype);
        evaluateRigidSortalClass(element, _class, markers, stereotype);
        evaluateMixinClass(element, _class, markers, stereotype);
        evaluateCategory(element, _class, markers, stereotype);
        evaluateMixin(element, _class, markers, stereotype);
        evaluateRelatorMediation(element, _class, markers, stereotype);
        evaluateRoleMediation(element, _class, markers, stereotype);
        evaluateRoleMixinMediation(element, _class, markers, stereotype);
        evaluateModeCharacterization(element, _class, markers, stereotype);
        evaluateMaterialAssociationDerivation(element, _class, markers, stereotype);
    }

    private void evaluateObjectClass(GNode element, Class _class, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, OBJECT_CLASS)) {
            if (countAncestorsWithStereotype(_class, SUBSTANCE_SORTAL) > 1) {
                addMarker(element, markers, formatClassMessage("Every Object Class must not have more than one Substance Sortal ancestor", _class));
            }
        }
    }

    private void evaluateSortalClass(GNode element, Class _class, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, SORTAL_CLASS) && !hasParentStereotype(stereotype, SUBSTANCE_SORTAL)) {
            if (countAncestorsWithStereotype(_class, SUBSTANCE_SORTAL) == 0) {
                addMarker(element, markers, formatClassMessage("Every non-abstract Sortal must have a Substance Sortal ancestor (or be a Substance Sortal)", _class));
            }
        }
    }

    private void evaluateSubstanceSortal(GNode element, Class _class, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, SUBSTANCE_SORTAL)) {
            if (countParentsWithStereotype(_class, RIGID_SORTAL_CLASS) != 0) {
                addMarker(element, markers, formatClassMessage("A Substance Sortal cannot have a Rigid Sortal parent", _class));
            }
        }
    }

    private void evaluateRigidSortalClass(GNode element, Class _class, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, RIGID_SORTAL_CLASS)) {
            if (countParentsWithStereotype(_class, "Anti Rigid Sortal Class") != 0 || countAncestorsWithStereotype(_class, "Role Mixin") != 0) {
                addMarker(element, markers, formatClassMessage("A Rigid Sortal cannot have an Anti-Rigid parent (Role, Phase and RoleMixin)", _class));
            }
        }
    }

    private void evaluateMixinClass(GNode element, Class _class, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, MIXIN_CLASS)) {
            if (countAncestorsWithStereotype(_class, SORTAL_CLASS) != 0) {
                addMarker(element, markers, "A Mixin Class (Category, Mixin, RoleMixin) cannot have a Sortal parent (Kind, Quantity, Collective, ");
            }
        }
    }

    private void evaluateCategory(GNode element, Class _class, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, CATEGORY)) {
            if (countAncestorsWithStereotype(_class, "Role Mixin") != 0) {
                addMarker(element, markers, formatClassMessage("A Category cannot have a Role Mixin parent", _class));
            }
        }
    }

    private void evaluateMixin(GNode element, Class _class, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, MIXIN)) {
            if (countAncestorsWithStereotype(_class, "Role Mixin") != 0) {
                addMarker(element, markers, formatClassMessage("A Mixin cannot have a RoleMixin parent", _class));
            }
        }
    }

    private void evaluateRelatorMediation(GNode element, Class _class, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, RELATOR)) {
            if (!hasAssociationWithStereotype(_class, QUALIFIED_MEDIATION)) {
                addMarker(element, markers, formatClassMessage("A Relator must be connected (directly or indirectly) to a Mediation", _class));
            } else if (countLowerAssociationWithStereotype(_class, QUALIFIED_MEDIATION) < 2) {
                addMarker(element, markers, formatClassMessage("The sum of the minimum cardinalities of the mediated ends must be greater or equal to 2", _class));
            }
        }
    }

    private void evaluateRoleMediation(GNode element, Class _class, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, ROLE)) {
            if (!hasAssociationWithStereotype(_class, QUALIFIED_MEDIATION)) {
                addMarker(element, markers, formatClassMessage("A Role must be connected (directly or indirectly) to a Mediation", _class));
            }
        }
    }

    private void evaluateRoleMixinMediation(GNode element, Class _class, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, ROLE_MIXIN)) {
            if (!hasAssociationWithStereotype(_class, QUALIFIED_MEDIATION)) {
                addMarker(element, markers, formatClassMessage("A RoleMixin must be connected (directly or indirectly) to a Mediation", _class));
            }
        }
    }

    private void evaluateModeCharacterization(GNode element, Class _class, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, MODE)) {
            if (!hasAssociationWithStereotype(_class, QUALIFIED_CHARACTERIZATION)) {
                addMarker(element, markers, formatClassMessage("A Mode must be connected (directly or indirectly) to a Characterization", _class));
            }
        }
    }

    private void evaluateMaterialAssociationDerivation(GNode element, Class _class, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, MATERIAL_ASSOCIATION)) {
            if (countAssociationWithStereotype(_class, DERIVATION) != 1) {
                addMarker(element, markers, formatClassMessage("Every Material Association must be connected to exactly one Derivation", _class));
            }
        }
    }

    private boolean hasAssociationWithStereotype(Classifier _class, String qualifiedName) {
        var hasStereotype = _class.getAssociations().stream().filter(association -> association.getAppliedStereotype(qualifiedName) != null).findFirst();
        if (hasStereotype.isPresent())
            return true;
        return _class.getGeneralizations().stream().anyMatch(generalization -> {
            var general = generalization.getGeneral();
            return hasAssociationWithStereotype(general, qualifiedName);
        });
    }

    private int countLowerAssociationWithStereotype(Classifier _class, String qualifiedName) {
        var lowerCount = _class.getAssociations().stream().filter(association -> association.getAppliedStereotype(qualifiedName) != null)
                .map(association -> association.getMemberEnds().get(0).getLower()).reduce(Integer::sum).orElse(0);
        var generalisation = _class.getGeneralizations();
        var count = 0;
        if (!generalisation.isEmpty()) {
            var general = generalisation.get(0).getGeneral();
            count += countLowerAssociationWithStereotype(general, qualifiedName);
        }
        return count + lowerCount;
    }

    private long countAssociationWithStereotype(Classifier _class, String qualifiedName) {
        var associations = _class.getAssociations().stream().filter(association -> association.getAppliedStereotype(qualifiedName) != null);
        var generalisation = _class.getGeneralizations();
        var count = 0;
        if (!generalisation.isEmpty()) {
            var general = generalisation.get(0).getGeneral();
            count += countLowerAssociationWithStereotype(general, qualifiedName);
        }
        return count + associations.count();
    }

    private void validateGeneralisation(GNode element, Generalization generalization, ArrayList<Marker> markers) {
        if ((hasParentStereotype(generalization.getGeneral(), OBJECT_CLASS) &&
                !hasParentStereotype(generalization.getSpecific(), OBJECT_CLASS)) ||
                (!hasParentStereotype(generalization.getGeneral(), OBJECT_CLASS) &&
                        hasParentStereotype(generalization.getSpecific(), OBJECT_CLASS))) {
            addMarker(element, markers, "An Object Class only participates in a Generalization with another Object Class");
        }
    }


    private void validateAssociation(GEdge element, Association association, ArrayList<Marker> markers) {
        if (association.getAppliedStereotypes().isEmpty()) {
            markers.add(newMarker("This Association has no Stereotype", element.getId()));
            return;
        }

        var stereotype = association.getAppliedStereotypes().get(0);

        validateDirectedBinaryAssociation(element, association, markers, stereotype);
        validateCharacterization(element, association, markers, stereotype);
        validateMediation(element, association, markers, stereotype);
        validateDerivation(element, association, markers, stereotype);
    }

    private void validateDirectedBinaryAssociation(GEdge element, Association association, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, DIRECTED_BINARY_ASSOCIATION)) {
            if (association.getMemberEnds().get(0).getLower() < 1) {
                addMarker(element, markers, "The source end minimum cardinality must be greater of equal to 1.");
            }
        }
    }

    private void validateDerivation(GEdge element, Association association, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, DERIVATION)) {
            var derivationSourceStereotype = association.getMemberEnds().get(1).getType().getAppliedStereotypes().stream().findFirst();
            var derivationTargetStereotype = association.getMemberEnds().get(0).getType().getAppliedStereotypes().stream().findFirst();
            if (derivationSourceStereotype.isEmpty() || hasParentStereotype(derivationSourceStereotype.get(), MATERIAL_ASSOCIATION)) {
                addMarker(element, markers, "(Derivation) The source must be a Material Association");
            }
            if (derivationTargetStereotype.isEmpty() || hasParentStereotype(derivationTargetStereotype.get(), RELATOR)) {
                addMarker(element, markers, "(Derivation) The target must be a Relator");
            }
            if (association.getMemberEnds().get(0).getLower() != 1 || association.getMemberEnds().get(0).getUpper() != 1) {
                addMarker(element, markers, "The Relator end cardinality is exactly one");
            }
        }
    }

    private void validateMediation(GEdge element, Association association, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, MEDIATION)) {
            var mediationEndStereotype = association.getMemberEnds().get(1).getType().getAppliedStereotypes().stream().findFirst();
            if (mediationEndStereotype.isEmpty() || !hasParentStereotype(mediationEndStereotype.get(), RELATOR)) {
                addMarker(element, markers, "(Mediation) The source must be a Relator");
            }
            if (association.getMemberEnds().get(1).getLower() < 1) {
                addMarker(element, markers, "The Mediated end minimum cardinality must be greater or equal to 1");
            }
        }
    }

    private void validateCharacterization(GEdge element, Association association, ArrayList<Marker> markers, Stereotype stereotype) {
        if (hasParentStereotype(stereotype, CHARACTERIZATION)) {
            var modeEndStereotype = association.getMemberEnds().get(1).getType().getAppliedStereotypes().stream().findFirst();
            if (modeEndStereotype.isEmpty() || !hasParentStereotype(modeEndStereotype.get(), MODE)) {
                addMarker(element, markers, "(Characterization) The source must be a Mode");
            }
            if (association.getMemberEnds().get(0).getLower() != 1 || association.getMemberEnds().get(0).getUpper() != 1) {
                addMarker(element, markers, "The Characterized end cardinality is exactly one.");
            }
        }
    }

    private static void addMarker(GModelElement element, ArrayList<Marker> markers, String message) {
        markers.add(newMarker(message, element.getId()));
    }

    protected boolean hasParentStereotype(Classifier stereotype, String name) {
        if (name.equals(stereotype.getName())) {
            return true;
        }
        var general = stereotype.getGeneralizations().stream().findFirst();
        return general.filter(generalization -> hasParentStereotype(generalization.getGeneral(), name)).isPresent();
    }

    protected int countAncestorsWithStereotype(Classifier _class, String name) {
        return _class.getGeneralizations().stream().map(Generalization::getGeneral).map(
                general -> {
                    var stereotype = _class.getAppliedStereotypes().stream().findFirst();

                    if (stereotype.isEmpty())
                        return 0;

                    var generalStereotypes = general.getAppliedStereotypes();
                    if (generalStereotypes.isEmpty())
                        return 0;
                    if (hasParentStereotype(generalStereotypes.get(0), name)) {
                        return 1 + countAncestorsWithStereotype(general, name);
                    }
                    return countAncestorsWithStereotype(general, name);
                }
        ).reduce(Integer::sum).orElse(0);
    }

    protected int countParentsWithStereotype(Classifier _class, String name) {
        var general = _class.getGeneralizations().stream().map(Generalization::getGeneral).findFirst();
        if (general.isPresent()) {
            var stereotype = _class.getAppliedStereotypes().stream().findFirst();

            if (stereotype.isEmpty())
                return 0;

            if (hasParentStereotype(general.get().getAppliedStereotypes().get(0), name)) {
                return 1;
            }
        }
        return 0;
    }

    private String formatClassMessage(String message, Classifier _class) {
        return '(' + _class.getName() + ") " + message;
    }

    private static Marker newMarker(String message, String elementId) {
        return new Marker(STEREOTYPE, message, elementId, MarkerKind.WARNING);
    }
}