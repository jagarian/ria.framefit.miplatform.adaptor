/**
 * 
 */
package lotte.fw.miplatform.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

import lotte.fw.exception.LotteRuntimeException;

import org.apache.log4j.Logger;

/**
* @Class Name : BusinessException.java
* @Description : A1에서 사용하는 DataProtorol로서 LinkedHashMap을 상속하였다.
 * 편리하게 형변환을 할수 있도록 getString, getInt등의 method를 제공한다.
 ** @Modification Information  
* @
* @  수정일      수정자              수정내용
* @ ---------	---------	-------------------------------
* @ 2012.09.10	최초생성
* 
* @author 
* @since 2012.09.10
* @version 1.0
* @see
* 
*  Copyright (C) by LDCC All right reserved.
*/
public class CastMap<K,V> extends LinkedHashMap<K,V> implements Serializable {
    
    private static final long serialVersionUID = 6723434363565878261L;
    private static final Logger log = Logger.getLogger(CastMap.class); // NOPMD by gunhlee on 09. 11. 2 오전 10:30
    
    protected boolean nullChange = false;
    
    public CastMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public CastMap(int initialCapacity) {
        super(initialCapacity);
    }
    
    public CastMap() {
        super();
    }
    
    public CastMap(
            int initialCapacity,
            float loadFactor,
            boolean accessOrder) {
            super(initialCapacity, loadFactor, accessOrder);
    }
    
    @Override
    public String toString() { // NOPMD by gunhlee on 09. 11. 2 오전 10:33
        return super.toString();
    }
    
    /**
     * @설명     : A1Map에서 null 처리를 할지 유무
     * @return
     */
    public boolean getNullChange() {
        return nullChange;
    }
    
    /**
     * @설명     : A1Map에 null 처리를 할지 setting
     * @param value
     */
    public void setNullChange(boolean value) {
        nullChange = value;
    }
    
    /** 
     * 전달받은 key값에 해당되는 BegDecimal값을 return한다.
     * 만일 Value가 존재하지 않을 경우에는 null또는 BigDecimal(0)(isNullToBlank()가 true인경우)을 return한다.
     * @param key String
     * @return BigDecimal
     */
     public BigDecimal getBigDecimal(Object key) {
         
         Object o = get(key);
         if (o == null) {
             if(nullChange) {
                 return new BigDecimal(0); // NOPMD by gunhlee on 09. 11. 2 오전 10:33
             } else {
                 return null; // NOPMD by gunhlee on 09. 11. 2 오전 10:33
             }
         } else {
             return (BigDecimal)o;
         }
     }
     
     /** 
      * 전달받은 key값에 해당되는 int값을 return한다.
      * 만일 Value가 존재하지 않을 경우에는 null또는 0(nullChange가 true인경우)을 return한다.
      * @param key String
      * @return int
      */              
      public int getInt( Object key ) {
          Object o = (Object)super.get(key);
          
          if ( o == null  ) {
              if( this.nullChange ) {
                  return 0; // NOPMD by gunhlee on 09. 11. 2 오전 10:33
              }else{
                  log.error("[LotteRuntimeException in MiPlatformMap] Key(" + key +") does not exist in MiPlatformMap or Key(" + key +")'s value is null."  );
                  throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Key(" + key +") does not exist in MiPlatformMap or Key(" + key +")'s value is null."  );
              }
          }else{
              Class classType = o.getClass() ;                
              if ( classType == Integer.class ){
                  return ((Integer)o).intValue(); // NOPMD by gunhlee on 09. 11. 2 오전 10:33
              } else if (classType == Short.class ) {
                  return ((Short)o).shortValue(); // NOPMD by gunhlee on 09. 11. 2 오전 10:33
              }               
    
              if ( classType == String.class ){
                  try{
                      return Integer.parseInt( o.toString() );
                  }catch(Exception e){
                      log.error("[LotteRuntimeException in MiPlatformMap] Key(" +key+")" + " Type(int) does not match : It's type is not int."  );                                            
                      throw new LotteRuntimeException("[LotteRuntimeException in MiPlatformMap] Value Type(int) does not match : It's type is not int.", e);                       
                  }
              }
              log.error("[LotteRuntimeException in MiPlatformMap] Key(" +key+")" + " Type(int) does not match : It's type is not int."  );                                            
              throw new LotteRuntimeException("[LotteRuntimeException in MiPlatformMap] Value Type(int) does not match : It's type is not int.");
          }
      }
      
      /** 
       * 전달받은 key값에 해당되는 double값을 return한다.
       * 만일 Value가 존재하지 않을 경우에는 null또는 0.0(nullChange가 true인경우)을 return한다.
       * @param key String
       * @return double
       */              
       public double getDouble( Object key ){
           Object o = (Object)super.get(key);
           
           if ( o == null  ) {
               if( this.nullChange ) {
                   return 0.0; // NOPMD by gunhlee on 09. 11. 2 오전 10:34
               }else{
                   log.error("[LotteRuntimeException in MiPlatformMap] Key(" + key +") does not exist in MiPlatformMap or Key(" + key +")'s value is null."  );
                   throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Key(" + key +") does not exist in MiPlatformMap or Key(" + key +")'s value is null."  );
               }
           }else{
               Class classType = o.getClass() ;
               if ( classType == Double.class ){
                   return ((Double)o).doubleValue(); // NOPMD by gunhlee on 09. 11. 2 오전 10:34
               } else if ( classType == Float.class ){
                   return ((Float)o).floatValue(); // NOPMD by gunhlee on 09. 11. 2 오전 10:34
               }
       
               if (  classType == String.class || classType == BigDecimal.class ){
                   try{
                       return Double.parseDouble( o.toString() );
                   }catch(Exception e){
                       log.error("[LotteRuntimeException in MiPlatformMap] Key(" +key+")" + " Type(double) does not match : It's type is not double."  );                                          
                       throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Value Type(double) does not match : It's type is not double.", e );                     
                   }
               }               
               log.error("[LotteRuntimeException in MiPlatformMap] Key(" +key+")" + " Type(double) does not match : It's type is not double."  );                                                          
               throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Value Type(double) does not match : It's type is not double." );
           }
       }   

       /** 
       * 전달받은 key값에 해당되는 float값을 return한다.
       * 만일 Value가 존재하지 않을 경우에는 null또는 0.0(nullChange가 true인경우)을 return한다.
       * @param key String
       * @return float
       */              
       public float getFloat( Object key ){
           Object o = (Object)super.get(key);
           
           if ( o == null  ) {
               if( this.nullChange ) {
                   return (float)0.0; // NOPMD by gunhlee on 09. 11. 2 오전 10:34
               }else{
                   log.error("[LotteRuntimeException in MiPlatformMap] Key(" + key +") does not exist in MiPlatformMap or Key(" + key +")'s value is null."  );
                   throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Key(" + key +") does not exist in MiPlatformMap or Key(" + key +")'s value is null."  );
               }
           }else{
                   
               Class classType = o.getClass() ;
               if ( classType == Float.class  ){
                   return ((Float)o).floatValue(); // NOPMD by gunhlee on 09. 11. 2 오전 10:34
               }
                  
               if ( classType == String.class || classType == BigDecimal.class ){
                   try{
                       return Float.parseFloat( o.toString() );
                   }catch(Exception e){
                       log.error("[LotteRuntimeException in MiPlatformMap] Key(" +key+")" + " Type(float) does not match : It's type is not float."  );                                            
                       throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Value Type(float) does not match : It's type is not float.", e );                       
                   }
               }               
               log.error("[LotteRuntimeException in MiPlatformMap] Key(" +key+")" + " Type(float) does not match : It's type is not float."  );                                                            
               throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Value Type(float) does not match : It's type is not float." );
           }
       }       
       
       /** 
       * 전달받은 key값에 해당되는 Value를 long로 return한다.
       * 만일 Value가 존재하지 않을 경우에는 null또는 0(nullChange가 true인경우)을 return한다.
       * 내부에 getString()을 이용하여 구현되어 있다.
       * @param key String
       * @return long
       */              
       public long getLong( Object key ) {
           Object o = (Object)super.get(key);
           
           if ( o == null  ) {
               if( this.nullChange ) {
                   return 0; // NOPMD by gunhlee on 09. 11. 2 오전 10:34
               }else{
                   log.error("[LotteRuntimeException in MiPlatformMap] Key(" + key +") does not exist in MiPlatformMap or Key(" + key +")'s value is null."  );
                   throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Key(" + key +") does not exist in MiPlatformMap or Key(" + key +")'s value is null."  );
               }
           }else{
               Class classType = o.getClass();
               if ( classType == Long.class ){
                   return ((Long)o).longValue(); // NOPMD by gunhlee on 09. 11. 2 오전 10:34
               } else if ( classType == Integer.class ){
                   return ((Integer)o).intValue(); // NOPMD by gunhlee on 09. 11. 2 오전 10:35
               } else if (classType == Short.class ) {
                   return ((Short)o).shortValue(); // NOPMD by gunhlee on 09. 11. 2 오전 10:35
               }           
                   
               if ( classType == String.class ){
                   try{
                       return Long.parseLong( o.toString() );
                   }catch(Exception e){
                       log.error("[LotteRuntimeException in MiPlatformMap] Key(" +key+")" + " Type(long) does not match : It's type is not long."  );                                          
                       throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Value Type(long) does not match : It's type is not long.", e );                     
                   }
               }               
               log.error("[LotteRuntimeException in MiPlatformMap] Key(" +key+")" + " Type(long) does not match : It's type is not long."  );                                                          
               throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Value Type(long) does not match : It's type is not long." );
           }
       }
       
       
       /** 
           * 전달받은 key값에 해당되는 Value를 boolean으로 return한다.
           * 만일 Value가 존재하지 않을 경우에는 Exception 또는 false( nullChange가 true인경우)을 return한다.
           * 내부에 getString()을 이용하여 구현되어 있다.
           * @param key String
           * @return boolean
           */
       public boolean getBoolean( Object key ) {
           Object o = (Object)super.get(key);
           
           if ( o == null  ) {
               if( this.nullChange ) {
                   return false; // NOPMD by gunhlee on 09. 11. 2 오전 10:35
               }else{
                   log.error("[LotteRuntimeException in MiPlatformMap] Key(" + key +") does not exist in MiPlatformMap or Key(" + key +")'s value is null."  );
                   throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Key(" + key +") does not exist in MiPlatformMap or Key(" + key +")'s value is null."  );
               }
           }else{
               if ( o.getClass().isInstance( new Boolean(true) ) ){ // NOPMD by gunhlee on 09. 11. 2 오전 10:32
                   return ((Boolean)o).booleanValue(); // NOPMD by gunhlee on 09. 11. 2 오전 10:35
               }
                   
               if ( o.getClass().isInstance( new String() ) ){ // NOPMD by gunhlee on 09. 11. 2 오전 10:32
                   try{
                       return Boolean.parseBoolean( o.toString() );
                   }catch(Exception e){
                       log.error("[LotteRuntimeException in MiPlatformMap] Key(" +key+")" + " Type(boolean) does not match : It's type is not boolean."  );
                       throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Value Type(boolean) does not match : It's type is not boolean.", e );
                   }
               }
               log.error("[LotteRuntimeException in MiPlatformMap] Key(" +key+")" + " Type(boolean) does not match : It's type is not boolean."  );
               throw new LotteRuntimeException( "[LotteRuntimeException in MiPlatformMap] Value Type(boolean) does not match : It's type is not boolean." );
           }
       }   
       
       /** 
       * 전달받은 key값에 해당되는 Value를 Object로 return한다.
       * 만일 Value가 존재하지 않을 경우에는 null또는 빈문자열(nullChange가 true인경우)을 return한다.
       * 내부에 getString()을 이용하여 구현되어 있다.
       * @param key String
       * @return String
       */                  
       public String getString( Object key ) {
           Object o = (Object)super.get(key);
           
           if ( o == null  ) {
               if(  this.nullChange ) {
                   return ""; // NOPMD by gunhlee on 09. 11. 2 오전 10:35
               }else{
                   return null; // NOPMD by gunhlee on 09. 11. 2 오전 10:35
               }
           }else{
               return o.toString();
           }
       }   
}
