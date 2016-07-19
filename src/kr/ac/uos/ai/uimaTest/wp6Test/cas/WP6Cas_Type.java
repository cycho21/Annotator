
/* First created by JCasGen Tue Jun 30 17:46:27 KST 2015 */
package kr.ac.uos.ai.uimaTest.wp6Test.cas;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Tue Jun 30 17:46:27 KST 2015
 * @generated */
public class WP6Cas_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (WP6Cas_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = WP6Cas_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new WP6Cas(addr, WP6Cas_Type.this);
  			   WP6Cas_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new WP6Cas(addr, WP6Cas_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = WP6Cas.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
 
  /** @generated */
  final Feature casFeat_processName;
  /** @generated */
  final int     casFeatCode_processName;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getProcessName(int addr) {
        if (featOkTst && casFeat_processName == null)
      jcas.throwFeatMissing("processName", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    return ll_cas.ll_getStringValue(addr, casFeatCode_processName);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setProcessName(int addr, String v) {
        if (featOkTst && casFeat_processName == null)
      jcas.throwFeatMissing("processName", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    ll_cas.ll_setStringValue(addr, casFeatCode_processName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_result;
  /** @generated */
  final int     casFeatCode_result;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getResult(int addr) {
        if (featOkTst && casFeat_result == null)
      jcas.throwFeatMissing("result", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    return ll_cas.ll_getRefValue(addr, casFeatCode_result);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setResult(int addr, int v) {
        if (featOkTst && casFeat_result == null)
      jcas.throwFeatMissing("result", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    ll_cas.ll_setRefValue(addr, casFeatCode_result, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getResult(int addr, int i) {
        if (featOkTst && casFeat_result == null)
      jcas.throwFeatMissing("result", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_result), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_result), i);
	return ll_cas.ll_getIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_result), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setResult(int addr, int i, int v) {
        if (featOkTst && casFeat_result == null)
      jcas.throwFeatMissing("result", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    if (lowLevelTypeChecks)
      ll_cas.ll_setIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_result), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_result), i);
    ll_cas.ll_setIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_result), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_startTime;
  /** @generated */
  final int     casFeatCode_startTime;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public long getStartTime(int addr) {
        if (featOkTst && casFeat_startTime == null)
      jcas.throwFeatMissing("startTime", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    return ll_cas.ll_getLongValue(addr, casFeatCode_startTime);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setStartTime(int addr, long v) {
        if (featOkTst && casFeat_startTime == null)
      jcas.throwFeatMissing("startTime", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    ll_cas.ll_setLongValue(addr, casFeatCode_startTime, v);}
    
  
 
  /** @generated */
  final Feature casFeat_endTime;
  /** @generated */
  final int     casFeatCode_endTime;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public long getEndTime(int addr) {
        if (featOkTst && casFeat_endTime == null)
      jcas.throwFeatMissing("endTime", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    return ll_cas.ll_getLongValue(addr, casFeatCode_endTime);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEndTime(int addr, long v) {
        if (featOkTst && casFeat_endTime == null)
      jcas.throwFeatMissing("endTime", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    ll_cas.ll_setLongValue(addr, casFeatCode_endTime, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public WP6Cas_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_processName = jcas.getRequiredFeatureDE(casType, "processName", "uima.cas.String", featOkTst);
    casFeatCode_processName  = (null == casFeat_processName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_processName).getCode();

 
    casFeat_result = jcas.getRequiredFeatureDE(casType, "result", "uima.cas.IntegerArray", featOkTst);
    casFeatCode_result  = (null == casFeat_result) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_result).getCode();

 
    casFeat_startTime = jcas.getRequiredFeatureDE(casType, "startTime", "uima.cas.Long", featOkTst);
    casFeatCode_startTime  = (null == casFeat_startTime) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_startTime).getCode();

 
    casFeat_endTime = jcas.getRequiredFeatureDE(casType, "endTime", "uima.cas.Long", featOkTst);
    casFeatCode_endTime  = (null == casFeat_endTime) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_endTime).getCode();

  }
}



    