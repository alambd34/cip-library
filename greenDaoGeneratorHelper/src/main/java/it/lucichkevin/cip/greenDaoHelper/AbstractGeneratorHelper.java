package it.lucichkevin.cip.greenDaoHelper;

import java.util.HashMap;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public abstract class AbstractGeneratorHelper {

    int version = 1;
    String base_package_name = "";
    Schema schema;

    String daoentitys_package_name = "database";
    String superentitys_package_name = "classes";

    HashMap<String,Entity> ENTITIES = new HashMap<String,Entity>();

    public AbstractGeneratorHelper( int version, String base_package_name ){
        setVersion(version);
        setPackage_name(base_package_name);

        createSchema();
    }

    public void createSchema(){
        schema = new Schema( getVersion(), getPackage_name() +"."+ daoentitys_package_name );
    }

    protected void addEntity( String entity_name, Entity entity ){
        ENTITIES.put( entity_name, entity );
    }

    protected Entity getEntity( String entity_name ){
        return ENTITIES.get(entity_name);
    }

    public void createClasses(){
        createClasses("../classesGenerated/"+ getPackage_name() +"/" );
    }

    public void createClasses( String path_where_create_classes ){
        try{
            new DaoGenerator().generateAll( getSchema(), path_where_create_classes );

//			Iterator it = ENTITIES.entrySet().iterator();
//		    while( it.hasNext() ){
//		        Map.Entry pairs = (Map.Entry) it.next();
//
//		        String entityName = (String) pairs.getKey();
//		        Entity value = (Entity) pairs.getValue();
//
//
//		        System.out.println( entityName + " = " + value.getTableName() );
//		    }

        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    protected Entity createEntity( String object_name ){
        return createEntity( object_name, object_name );
    }

    protected Entity createEntity( String object_name, String table_name ){
        return createEntity( object_name, table_name, getPackage_name() +"."+ superentitys_package_name );
    }

    protected Entity createEntity( String object_name, String table_name, String package_superclass ){

//		Entity entity = getSchema().addEntity( "Entity_"+ object_name );
        Entity entity = getSchema().addEntity( object_name );
        entity.setTableName( table_name );
        entity.setSuperclass( package_superclass +".Super"+ object_name );
        entity.implementsSerializable();

        return entity;
    }



    ////////////////////////////////
    //	Getters and Setters

    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    public String getPackage_name() {
        return base_package_name;
    }
    public void setPackage_name(String package_name) {
        this.base_package_name = package_name;
    }

    public Schema getSchema() {
        return schema;
    }
    public void setSchema(Schema schema) {
        this.schema = schema;
    }

}
