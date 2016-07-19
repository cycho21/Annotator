

/* First created by JCasGen Tue Jun 30 17:46:27 KST 2015 */
package kr.ac.uos.ai.uimaTest.wp6Test.cas;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.cas.IntegerArray;


/** 
 * Updated by JCasGen Tue Jun 30 17:46:27 KST 2015
 * XML source: D:/WorkSpace/UimaWS/uimaTest/desc/dummyWP6/DummyWP6Types.xml
 * @generated */

public class WP6Cas extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(WP6Cas.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected WP6Cas() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public WP6Cas(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public WP6Cas(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public WP6Cas(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: processName

  /** getter for processName - gets 
   * @generated
   * @return value of the feature 
   */
  public String getProcessName() {
    if (WP6Cas_Type.featOkTst && ((WP6Cas_Type)jcasType).casFeat_processName == null)
      jcasType.jcas.throwFeatMissing("processName", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    return jcasType.ll_cas.ll_getStringValue(addr, ((WP6Cas_Type)jcasType).casFeatCode_processName);}
    
  /** setter for processName - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setProcessName(String v) {
    if (WP6Cas_Type.featOkTst && ((WP6Cas_Type)jcasType).casFeat_processName == null)
      jcasType.jcas.throwFeatMissing("processName", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    jcasType.ll_cas.ll_setStringValue(addr, ((WP6Cas_Type)jcasType).casFeatCode_processName, v);}    
   
    
  //*--------------*
  //* Feature: result

  /** getter for result - gets 
   * @generated
   * @return value of the feature 
   */
  public IntegerArray getResult() {
    if (WP6Cas_Type.featOkTst && ((WP6Cas_Type)jcasType).casFeat_result == null)
      jcasType.jcas.throwFeatMissing("result", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    return (IntegerArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((WP6Cas_Type)jcasType).casFeatCode_result)));}
    
  /** setter for result - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setResult(IntegerArray v) {
    if (WP6Cas_Type.featOkTst && ((WP6Cas_Type)jcasType).casFeat_result == null)
      jcasType.jcas.throwFeatMissing("result", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    jcasType.ll_cas.ll_setRefValue(addr, ((WP6Cas_Type)jcasType).casFeatCode_result, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for result - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public int getResult(int i) {
    if (WP6Cas_Type.featOkTst && ((WP6Cas_Type)jcasType).casFeat_result == null)
      jcasType.jcas.throwFeatMissing("result", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((WP6Cas_Type)jcasType).casFeatCode_result), i);
    return jcasType.ll_cas.ll_getIntArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((WP6Cas_Type)jcasType).casFeatCode_result), i);}

  /** indexed setter for result - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setResult(int i, int v) { 
    if (WP6Cas_Type.featOkTst && ((WP6Cas_Type)jcasType).casFeat_result == null)
      jcasType.jcas.throwFeatMissing("result", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((WP6Cas_Type)jcasType).casFeatCode_result), i);
    jcasType.ll_cas.ll_setIntArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((WP6Cas_Type)jcasType).casFeatCode_result), i, v);}
   
    
  //*--------------*
  //* Feature: startTime

  /** getter for startTime - gets 
   * @generated
   * @return value of the feature 
   */
  public long getStartTime() {
    if (WP6Cas_Type.featOkTst && ((WP6Cas_Type)jcasType).casFeat_startTime == null)
      jcasType.jcas.throwFeatMissing("startTime", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    return jcasType.ll_cas.ll_getLongValue(addr, ((WP6Cas_Type)jcasType).casFeatCode_startTime);}
    
  /** setter for startTime - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setStartTime(long v) {
    if (WP6Cas_Type.featOkTst && ((WP6Cas_Type)jcasType).casFeat_startTime == null)
      jcasType.jcas.throwFeatMissing("startTime", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    jcasType.ll_cas.ll_setLongValue(addr, ((WP6Cas_Type)jcasType).casFeatCode_startTime, v);}    
   
    
  //*--------------*
  //* Feature: endTime

  /** getter for endTime - gets 
   * @generated
   * @return value of the feature 
   */
  public long getEndTime() {
    if (WP6Cas_Type.featOkTst && ((WP6Cas_Type)jcasType).casFeat_endTime == null)
      jcasType.jcas.throwFeatMissing("endTime", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    return jcasType.ll_cas.ll_getLongValue(addr, ((WP6Cas_Type)jcasType).casFeatCode_endTime);}
    
  /** setter for endTime - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEndTime(long v) {
    if (WP6Cas_Type.featOkTst && ((WP6Cas_Type)jcasType).casFeat_endTime == null)
      jcasType.jcas.throwFeatMissing("endTime", "kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");
    jcasType.ll_cas.ll_setLongValue(addr, ((WP6Cas_Type)jcasType).casFeatCode_endTime, v);}    
  }

    