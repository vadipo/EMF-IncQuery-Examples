package org.eclipse.viatra2.emf.incquery.base.test.listener;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.viatra2.emf.incquery.base.api.FeatureListener;
import org.eclipse.viatra2.emf.incquery.base.test.IncQueryBaseTest;
import org.eclipse.viatra2.emf.incquery.base.test.util.ResourceAccess;
import org.junit.Test;

import school.Course;
import school.SchoolFactory;
import school.SchoolPackage;

public class FeatureListenerTest extends IncQueryBaseTest {

	public FeatureListenerTest(Notifier notifier) {
		super(notifier, false);
	}

	@Test
	public void featureListenerTest() {
		final Course newCourse = SchoolFactory.eINSTANCE.createCourse();
		final String newCourseSubject = "NewCourse";
		newCourse.setSubject(newCourseSubject);

		Set<EStructuralFeature> features = new HashSet<EStructuralFeature>();
		features.add(SchoolPackage.eINSTANCE.getCourse_Subject());

		FeatureListener featureListener = new FeatureListener() {

			@Override
			public void featureInserted(EObject host, EStructuralFeature feature, Object value) {
				assertTrue(
						host.equals(newCourse) && 
						feature.equals(SchoolPackage.eINSTANCE.getCourse_Subject()) && 
						((String)value).equals(newCourseSubject));
			}

			@Override
			public void featureDeleted(EObject host, EStructuralFeature feature, Object value) {
				assertTrue(
						host.equals(newCourse) && 
						feature.equals(SchoolPackage.eINSTANCE.getCourse_Subject()) && 
						((String)value).equals(newCourseSubject));				
			}
		};
		
		navigationHelper.registerEStructuralFeatures(features);
		navigationHelper.registerFeatureListener(features, featureListener);

		final RecordingCommand command = new RecordingCommand(ResourceAccess.getTransactionalEditingDomain()) {
			@Override
			protected void doExecute() {
				ResourceAccess.getEObject().getCourses().add(newCourse);
			}
		};
		try {
			ResourceAccess.getTransactionalEditingDomain().getCommandStack().execute(command);
			ResourceAccess.getTransactionalEditingDomain().getCommandStack().undo();
		}
		finally {
			navigationHelper.unregisterEStructuralFeatures(features);
			navigationHelper.unregisterFeatureListener(features, featureListener);
		}
	}
	
}