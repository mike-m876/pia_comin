package com.drinksafe.manager.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DrinkSafeDatabase_Impl extends DrinkSafeDatabase {
  private volatile BebidaDao _bebidaDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `bebidas` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT NOT NULL, `marca` TEXT NOT NULL, `tipo` TEXT NOT NULL, `fechaRegistro` TEXT NOT NULL, `datosEspectrales` TEXT NOT NULL, `conductividad` REAL NOT NULL, `temperatura` REAL NOT NULL, `alcoholEstimado` REAL NOT NULL, `notas` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a7ddcead387e1da167bea45eac10a547')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `bebidas`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsBebidas = new HashMap<String, TableInfo.Column>(10);
        _columnsBebidas.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBebidas.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBebidas.put("marca", new TableInfo.Column("marca", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBebidas.put("tipo", new TableInfo.Column("tipo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBebidas.put("fechaRegistro", new TableInfo.Column("fechaRegistro", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBebidas.put("datosEspectrales", new TableInfo.Column("datosEspectrales", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBebidas.put("conductividad", new TableInfo.Column("conductividad", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBebidas.put("temperatura", new TableInfo.Column("temperatura", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBebidas.put("alcoholEstimado", new TableInfo.Column("alcoholEstimado", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBebidas.put("notas", new TableInfo.Column("notas", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBebidas = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBebidas = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBebidas = new TableInfo("bebidas", _columnsBebidas, _foreignKeysBebidas, _indicesBebidas);
        final TableInfo _existingBebidas = TableInfo.read(db, "bebidas");
        if (!_infoBebidas.equals(_existingBebidas)) {
          return new RoomOpenHelper.ValidationResult(false, "bebidas(com.drinksafe.manager.data.models.Bebida).\n"
                  + " Expected:\n" + _infoBebidas + "\n"
                  + " Found:\n" + _existingBebidas);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "a7ddcead387e1da167bea45eac10a547", "672d32cbfdd7fb6a271a365a900c9d20");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "bebidas");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `bebidas`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(BebidaDao.class, BebidaDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public BebidaDao bebidaDao() {
    if (_bebidaDao != null) {
      return _bebidaDao;
    } else {
      synchronized(this) {
        if(_bebidaDao == null) {
          _bebidaDao = new BebidaDao_Impl(this);
        }
        return _bebidaDao;
      }
    }
  }
}
