// $Id$
// Copyright (c) 2003 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.model;

import java.util.*;

// NS-UML imports:
import ru.novosoft.uml.foundation.core.*;
import ru.novosoft.uml.foundation.data_types.*;
import ru.novosoft.uml.foundation.extension_mechanisms.*;
import ru.novosoft.uml.behavior.collaborations.*;
import ru.novosoft.uml.model_management.*;

// GEF imports:
import org.tigris.gef.base.*;
// import org.tigris.gef.presentation.*;
// import org.tigris.gef.util.*;

// Diagram model imports:
import org.argouml.model.uml.foundation.core.*;
import org.argouml.uml.*;
// import org.argouml.uml.diagram.ui.*;
// import org.argouml.uml.diagram.deployment.ui.*;
// import org.argouml.uml.diagram.static_structure.ui.*;


/**
 * Facade object for the Model component in ArgoUML.
 *
 * The purpose of this Facade object is to allow for decoupling other modules
 * from the insides of the model. For this purpose all of the methods in this
 * class give away and accept handles (of type java.lang.Object) to the 
 * objects within the model.
 *
 * All methods in this facade are static.
 * 
 * <p>Signature for all recognizers in this Facade:
 * public static boolean isA<TYPE>(Object handle)
 * public static boolean is<PROPERTY>(Object handle)
 *
 * <p>Signature for all getters in this Facade:
 * public static Object get<TYPE>(Object handle) - 1..1
 * public static Iterator get<TYPES>(Object handle) - 0..*
 * public static String getName(Object handle) - Name
 *  
 * @stereotype utility
 * @author Linus Tolke
 */
public class ModelFacade {
    /** Constructor that forbids instantiation.
     */
    private ModelFacade() {
    }

    ////////////////////////////////////////////////////////////////
    // Recognizer methods for the UML model (in alphabetic order)

    /** Recognizer for Association.
     *
     * @param handle candidate
     * @returns true if handle is an Association
     */
    public static boolean isAAssociation(Object handle) {
	return handle instanceof MAssociation;
    }

    /** Recognizer for AssociationRole
     *
     * @param handle candidate
     * @returns true if handle is an AssociationRole
     */
    public static boolean isAAssociationRole(Object handle) {
	return handle instanceof MAssociationRole;
    }	    

    /** Recognizer for abstract classes and operations.
     *
     * @param handle candidate
     * @returns true if handle is abstract.
     */
    public static boolean isAbstract(Object handle) {
	if (handle instanceof MOperation)
	    return ((MOperation) handle).isAbstract();
	if (handle instanceof MGeneralizableElement)
	    return ((MGeneralizableElement) handle).isAbstract();
	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }

    /** Recognizer for Class
     *
     * @param handle candidate
     * @returns true if handle is a Class
     */
    public static boolean isAClass(Object handle) {
	return handle instanceof MClass;
    }

    /** Recognizer for Classifier
     *
     * @param handle candidate
     * @returns true if handle is a Classifier
     */
    public static boolean isAClassifier(Object handle) {
	return handle instanceof MClassifier;
    }

    /** Recognizer for GeneralizableElement
     *
     * @param handle candidate
     * @returns true if handle is a GeneralizableElement
     */
    public static boolean isAGeneralizableElement(Object handle) {
	return handle instanceof MGeneralizableElement;
    }

    /** Recognizer for Package
     *
     * @param handle candidate
     * @returns true if handle is a Package
     */
    public static boolean isAPackage(Object handle) {
	return handle instanceof MPackage;
    }

    /** Recognizer for attributes with instance scope.
     *
     * @param handle candidate
     * @returns true if handle has instance scope.
     */
    public static boolean isInstanceScope(Object handle) {
	if (handle instanceof MAttribute) {
	    MAttribute a = (MAttribute)handle;
	    return MScopeKind.INSTANCE.equals(a.getOwnerScope());
	}
	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }

    /** Recognizer for leafs
     *
     * @param handle candidate GeneralizableElement
     * @returns true if handle is a leaf
     */
    public static boolean isLeaf(Object handle) {
	if (handle instanceof MGeneralizableElement) {
	    return ((MGeneralizableElement)handle).isLeaf();
	}
	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }

    /** Recognizer for Navigable elements
     *
     * @param handle candidate
     * @returns true if handle is navigable
     */
    public static boolean isNavigable(Object handle) {
	if (handle instanceof MAssociationEnd) {
	    return ((MAssociationEnd) handle).isNavigable();
	}

	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }


    /** Recognizer for primary objects.
     * A primary object is an object that is created by the parser or 
     * by a user.
     * Object that are created when importing some other object are not.
     *
     * @param handle candidate
     * @returns true if primary object.
     */
    public static boolean isPrimaryObject(Object handle) {
	if (handle instanceof MModelElement) {
	    MModelElement element = (MModelElement) handle;
	    for(Iterator i = element.getTaggedValues().iterator(); 
		i.hasNext(); 
		) {
		MTaggedValue tv = (MTaggedValue)i.next();
		if ((MMUtil.GENERATED_TAG).equals(tv.getTag())) {
		    return false;
		}
	    }
	    return true;
	}
	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }

    /** Recognizer for singleton.
     *
     * @param handle candidate
     * @returns true if handle is a singleton.
     */
    public static boolean isSingleton(Object handle) {
	if (handle instanceof MModelElement) {
	    MModelElement element = (MModelElement) handle;
	    MStereotype meSt = element.getStereotype();

	    if (meSt == null) return false;
	    
	    String name = meSt.getName();
	    if (name == null) return false;

	    return name.equals("singleton") || name.equals("Singleton");
	}

	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }





    ////////////////////////////////////////////////////////////////
    // Recognizer methods for the diagrams (in alphabetic order)

    /** Recognizer for Diagram.
     *
     * @param handle candidate
     * @returns true if handle is a diagram.
     */
    public static boolean isADiagram(Object handle) { 
	return handle instanceof Diagram;
    }



    ////////////////////////////////////////////////////////////////
    // Getters for the UML model (in alphabetic order)

    /** The list of Association Ends
     *
     * @param handle the object that we get the association ends from.
     * @return Iterator with association ends.
     */
    public static Iterator getAssociationEnds(Object handle) {
	if (handle instanceof MClass) {
	    Collection endc = ((MClass)handle).getAssociationEnds();
	    if (endc == null)
		return emptyIterator();
	    return endc.iterator();
	}

	//...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }


    /** The list of Attributes.
     *
     * Only Classifiers have attributes so if this is called with something
     * else than a Classifier, an empty iterator is returned.
     *
     * @param handle classifier to examine.
     * @return iterator with attributes.
     */
    public static Iterator getAttributes(Object handle) {
	if (handle instanceof MClassifier) {
	    MClassifier c = (MClassifier) handle;

	    // TODO: We are converting back and forth between collections and
	    // iterators. I (Linus) prefer iterators.
	    return CoreHelper.getHelper().getAttributes(c).iterator();
	}

	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }

    /** The list of Connections to an Association.
     *
     * @param handle to the association.
     * @return an Iterator with all connections.
     */
    public static Iterator getConnections(Object handle) {
	if (handle instanceof MAssociation) {
	    return ((MAssociation) handle).getConnections().iterator();
	}

	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }

    /** The list of Operations of this classifier and all inherited.
     *
     * @param handle classifier to examine.
     * @return Iterator with operations.
     */
    public static Iterator getOperationsInh(Object handle) {
	if (handle instanceof MClassifier) {
	    MClassifier c = (MClassifier) handle;

	    // TODO: We are converting back and forth between collections and
	    // iterators. I (Linus) prefer iterators.
	    return CoreHelper.getHelper().getOperationsInh(c).iterator();
	}

	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }

    /** The list of Associations Ends connected to this association end
     *
     * @param handle association end to start from
     * @returns Iterator with all connected association ends.
     */
    public static Iterator getOtherAssociationEnds(Object handle) {
	if (handle instanceof MAssociationEnd) {
	    MAssociation a = ((MAssociationEnd)handle).getAssociation();

	    if (a == null)
		return emptyIterator();

	    Collection allEnds = a.getConnections();
	    if (allEnds == null)
		return emptyIterator();

	    // TODO: An Iterator filter would be nice here instead of the 
	    // mucking around with the Collection.
	    allEnds = new ArrayList(allEnds);
	    allEnds.remove(handle);
	    return allEnds.iterator();
	}

	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }

    /** The list of owned elements of the the package.
     *
     * @param handle package to retrieve from.
     * @return Iterator with operations
     */
    public static Iterator getOwnedElements(Object handle) {
	if (handle instanceof MNamespace) {
	    return ((MNamespace)handle).getOwnedElements().iterator();
	}

	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }

    /** The list of Specializations from a GeneralizableElement.
     *
     * @param handle GeneralizableElement to retrieve from.
     * @return Iterator with Specializations.
     */
    public static Iterator getSpecializations(Object handle) {
	if (handle instanceof MGeneralizableElement) {
	    MGeneralizableElement ge = (MGeneralizableElement)handle;
	    return ge.getSpecializations().iterator();
	}

	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }



    ////////////////////////////////////////////////////////////////
    // Common getters

    /** The name of a model element or some diagram part.
     *
     * @param handle that points out the object.
     * @returns the name
     */
    public static String getName(Object handle) {
	if (handle instanceof MModelElement) {
	    MModelElement me = (MModelElement) handle;

	    return me.getName();
	}

	if (handle instanceof Diagram) {
	    Diagram d = (Diagram) handle;

	    return d.getName();
	}

	// ...
	throw new IllegalArgumentException("Unrecognized object " + handle);
    }


    ////////////////////////////////////////////////////////////////
    // Convenience methods

    /** The empty set.
     *
     * @returns an empty iterator.
     */
    private static Iterator emptyIterator() {
	return Collections.EMPTY_SET.iterator();
    }
}
