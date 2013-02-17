package school.coursewithnamelongerthanweight;

import java.util.Map;
import org.eclipse.incquery.runtime.extensibility.IMatchChecker;
import org.eclipse.incquery.runtime.rete.tuple.Tuple;

/**
 * A xbase xexpression evaluator tailored for the school.courseWithNameLongerThanWeight pattern.
 */
public class CourseWithNameLongerThanWeightEvaluator1_1 implements IMatchChecker {
  /**
   * The raw java code generated from the xbase xexpression by xtext.
   */
  private Boolean evaluateXExpressionGenerated(final String CName, final Integer W) {
    String _CName = CName;
    int _length = _CName.length();
    Integer _W = W;
    boolean _greaterThan = (_length > (_W).intValue());
    return Boolean.valueOf(_greaterThan);
  }
  
  /**
   * A wrapper method for calling the generated java method with the correct attributes.
   */
  @Override
  public Boolean evaluateXExpression(final Tuple tuple, final Map<String,Integer> tupleNameMap) {
    int CNamePosition = tupleNameMap.get("CName");
    java.lang.String CName = (java.lang.String) tuple.get(CNamePosition);
    int WPosition = tupleNameMap.get("W");
    java.lang.Integer W = (java.lang.Integer) tuple.get(WPosition);
    return evaluateXExpressionGenerated(CName, W);
  }
}
