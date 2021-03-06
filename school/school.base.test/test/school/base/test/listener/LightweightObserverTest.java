/*******************************************************************************
 * Copyright (c) 2010-2013, Abel Hegedus, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Abel Hegedus - initial API and implementation
 *******************************************************************************/
package school.base.test.listener;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.viatra.query.runtime.base.api.LightweightEObjectObserver;
import org.eclipse.viatra.query.runtime.base.api.LightweightEObjectObserverAdapter;

import com.google.common.collect.Sets;

import school.School;
import school.SchoolPackage;
import school.base.test.util.ModelManager;

/**
 * @author Abel Hegedus
 *
 */
public class LightweightObserverTest extends QueryBaseListenerTest {

    private LightweightEObjectObserver observer;
    private LightweightEObjectObserverAdapter adapter;
    private EStructuralFeature feature;
    private School firstSchool;
    private School secondSchool;
    
    /**
     * @param notifier
     */
    public LightweightObserverTest(Notifier notifier) {
        super(notifier, false);
        
        feature = SchoolPackage.eINSTANCE.getSchool_Courses();
        firstSchool = (School) ModelManager.getModel().getResources().get(0).getContents().get(0);
        secondSchool = (School) ModelManager.getModel().getResources().get(1).getContents().get(0);
        observer = new LightweightEObjectObserver() {

            @Override
            public void notifyFeatureChanged(EObject host, EStructuralFeature feature, Notification notification) {
                boolean courseAdded = host.equals(firstSchool) && feature.equals(SchoolPackage.eINSTANCE.getSchool_Courses())
                        && newCourse.equals(notification.getNewValue());
                boolean courseRemoved = host.equals(firstSchool) && feature.equals(SchoolPackage.eINSTANCE.getSchool_Courses())
                        && notification.getNewValue() == null;
                assertTrue(courseAdded || courseRemoved);
            }
        };
        
        adapter = new LightweightEObjectObserverAdapter(Sets.<EStructuralFeature>newHashSet(feature)) {
            
            @Override
            public void observedFeatureUpdate(EObject host, EStructuralFeature feature, Notification notification) {
                boolean courseAdded = host.equals(firstSchool) && feature.equals(SchoolPackage.eINSTANCE.getSchool_Courses())
                        && newCourse.equals(notification.getNewValue());
                boolean courseRemoved = host.equals(firstSchool) && feature.equals(SchoolPackage.eINSTANCE.getSchool_Courses())
                        && notification.getNewValue() == null;
                assertTrue(courseAdded || courseRemoved);
            }
        };
        // TODO test multiple observers added to same listener
    }

    @Override
    public void registerListener() {
        navigationHelper.addLightweightEObjectObserver(observer, firstSchool);
        navigationHelper.addLightweightEObjectObserver(adapter, firstSchool);
        navigationHelper.addLightweightEObjectObserver(observer, secondSchool);
        navigationHelper.addLightweightEObjectObserver(adapter, secondSchool);
    }

    @Override
    public void unregisterListener() {
        navigationHelper.removeLightweightEObjectObserver(observer, firstSchool);
        navigationHelper.removeLightweightEObjectObserver(adapter, firstSchool);
        navigationHelper.removeLightweightEObjectObserver(observer, secondSchool);
        navigationHelper.removeLightweightEObjectObserver(adapter, secondSchool);
    }

}
