package it.lucichkevin.ciphelpers.greenDao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import it.lucichkevin.cip.Utils;

/**
 *	@author		Kevin Lucich	03/03/15
 *
 *	Questa classe dovrebbe essere un helper per ottenere un'istanza valida del Database, tuttavia
 *	siamo costretti ad avere degli oggetti creati solo DOPO l'utilizzo di GreenDao.. uff...
 *	l'unica è che gli oggetti di GreenDao usassero una superclasse comune! ri-uff!
 *
 *	@deprecated
 */
public abstract class DatabaseHelper{

	///
	protected static String db_name = "";
	///
	protected static SQLiteDatabase db = null;
	protected static DaoSession daoSession = null;

	public static SQLiteDatabase getDatabase(){
		if( db == null ){
			DaoMaster.OpenHelper helper = new DaoMaster.OpenHelper( Utils.getContext(), db_name, null ){
				@Override
				public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ){
					//  Do nothing...
				}
			};
			// Access the database using the helper
			db = helper.getWritableDatabase();
		}
		return db;
	}


	public static DaoSession getDaoSession(){
		return getDaoSession(false);
	}

	public static DaoSession getDaoSession( boolean reliable_session ){

		if( daoSession == null || reliable_session ){
			// Construct the DaoMaster which brokers DAOs for the Domain Objects
			DaoMaster daoMaster = new DaoMaster( getDatabase() );
			daoSession = daoMaster.newSession();
		}

		return daoSession;
	}

}


class DaoSession{}

class DaoMaster{
	public DaoMaster( SQLiteDatabase database ){}
	public DaoSession newSession(){
		return null;
	}

	public static class OpenHelper {
		public OpenHelper( Context context, String db_name, Object o ){}
		public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ){}
		public SQLiteDatabase getWritableDatabase(){
			return null;
		}
	}
}