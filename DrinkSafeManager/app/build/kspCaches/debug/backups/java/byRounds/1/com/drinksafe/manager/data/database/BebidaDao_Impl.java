package com.drinksafe.manager.data.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.drinksafe.manager.data.models.Bebida;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BebidaDao_Impl implements BebidaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Bebida> __insertionAdapterOfBebida;

  private final EntityDeletionOrUpdateAdapter<Bebida> __deletionAdapterOfBebida;

  private final EntityDeletionOrUpdateAdapter<Bebida> __updateAdapterOfBebida;

  public BebidaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBebida = new EntityInsertionAdapter<Bebida>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `bebidas` (`id`,`nombre`,`marca`,`tipo`,`fechaRegistro`,`datosEspectrales`,`conductividad`,`temperatura`,`alcoholEstimado`,`notas`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Bebida entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getNombre());
        statement.bindString(3, entity.getMarca());
        statement.bindString(4, entity.getTipo());
        statement.bindString(5, entity.getFechaRegistro());
        statement.bindString(6, entity.getDatosEspectrales());
        statement.bindDouble(7, entity.getConductividad());
        statement.bindDouble(8, entity.getTemperatura());
        statement.bindDouble(9, entity.getAlcoholEstimado());
        statement.bindString(10, entity.getNotas());
      }
    };
    this.__deletionAdapterOfBebida = new EntityDeletionOrUpdateAdapter<Bebida>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `bebidas` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Bebida entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfBebida = new EntityDeletionOrUpdateAdapter<Bebida>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `bebidas` SET `id` = ?,`nombre` = ?,`marca` = ?,`tipo` = ?,`fechaRegistro` = ?,`datosEspectrales` = ?,`conductividad` = ?,`temperatura` = ?,`alcoholEstimado` = ?,`notas` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Bebida entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getNombre());
        statement.bindString(3, entity.getMarca());
        statement.bindString(4, entity.getTipo());
        statement.bindString(5, entity.getFechaRegistro());
        statement.bindString(6, entity.getDatosEspectrales());
        statement.bindDouble(7, entity.getConductividad());
        statement.bindDouble(8, entity.getTemperatura());
        statement.bindDouble(9, entity.getAlcoholEstimado());
        statement.bindString(10, entity.getNotas());
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public Object insertar(final Bebida bebida, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBebida.insertAndReturnId(bebida);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object eliminar(final Bebida bebida, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBebida.handle(bebida);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object actualizar(final Bebida bebida, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBebida.handle(bebida);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Bebida>> obtenerTodas() {
    final String _sql = "SELECT * FROM bebidas ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bebidas"}, new Callable<List<Bebida>>() {
      @Override
      @NonNull
      public List<Bebida> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfMarca = CursorUtil.getColumnIndexOrThrow(_cursor, "marca");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfFechaRegistro = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaRegistro");
          final int _cursorIndexOfDatosEspectrales = CursorUtil.getColumnIndexOrThrow(_cursor, "datosEspectrales");
          final int _cursorIndexOfConductividad = CursorUtil.getColumnIndexOrThrow(_cursor, "conductividad");
          final int _cursorIndexOfTemperatura = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatura");
          final int _cursorIndexOfAlcoholEstimado = CursorUtil.getColumnIndexOrThrow(_cursor, "alcoholEstimado");
          final int _cursorIndexOfNotas = CursorUtil.getColumnIndexOrThrow(_cursor, "notas");
          final List<Bebida> _result = new ArrayList<Bebida>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Bebida _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpMarca;
            _tmpMarca = _cursor.getString(_cursorIndexOfMarca);
            final String _tmpTipo;
            _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            final String _tmpFechaRegistro;
            _tmpFechaRegistro = _cursor.getString(_cursorIndexOfFechaRegistro);
            final String _tmpDatosEspectrales;
            _tmpDatosEspectrales = _cursor.getString(_cursorIndexOfDatosEspectrales);
            final float _tmpConductividad;
            _tmpConductividad = _cursor.getFloat(_cursorIndexOfConductividad);
            final float _tmpTemperatura;
            _tmpTemperatura = _cursor.getFloat(_cursorIndexOfTemperatura);
            final float _tmpAlcoholEstimado;
            _tmpAlcoholEstimado = _cursor.getFloat(_cursorIndexOfAlcoholEstimado);
            final String _tmpNotas;
            _tmpNotas = _cursor.getString(_cursorIndexOfNotas);
            _item = new Bebida(_tmpId,_tmpNombre,_tmpMarca,_tmpTipo,_tmpFechaRegistro,_tmpDatosEspectrales,_tmpConductividad,_tmpTemperatura,_tmpAlcoholEstimado,_tmpNotas);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object obtenerPorId(final int id, final Continuation<? super Bebida> $completion) {
    final String _sql = "SELECT * FROM bebidas WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Bebida>() {
      @Override
      @Nullable
      public Bebida call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfMarca = CursorUtil.getColumnIndexOrThrow(_cursor, "marca");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfFechaRegistro = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaRegistro");
          final int _cursorIndexOfDatosEspectrales = CursorUtil.getColumnIndexOrThrow(_cursor, "datosEspectrales");
          final int _cursorIndexOfConductividad = CursorUtil.getColumnIndexOrThrow(_cursor, "conductividad");
          final int _cursorIndexOfTemperatura = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatura");
          final int _cursorIndexOfAlcoholEstimado = CursorUtil.getColumnIndexOrThrow(_cursor, "alcoholEstimado");
          final int _cursorIndexOfNotas = CursorUtil.getColumnIndexOrThrow(_cursor, "notas");
          final Bebida _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpMarca;
            _tmpMarca = _cursor.getString(_cursorIndexOfMarca);
            final String _tmpTipo;
            _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            final String _tmpFechaRegistro;
            _tmpFechaRegistro = _cursor.getString(_cursorIndexOfFechaRegistro);
            final String _tmpDatosEspectrales;
            _tmpDatosEspectrales = _cursor.getString(_cursorIndexOfDatosEspectrales);
            final float _tmpConductividad;
            _tmpConductividad = _cursor.getFloat(_cursorIndexOfConductividad);
            final float _tmpTemperatura;
            _tmpTemperatura = _cursor.getFloat(_cursorIndexOfTemperatura);
            final float _tmpAlcoholEstimado;
            _tmpAlcoholEstimado = _cursor.getFloat(_cursorIndexOfAlcoholEstimado);
            final String _tmpNotas;
            _tmpNotas = _cursor.getString(_cursorIndexOfNotas);
            _result = new Bebida(_tmpId,_tmpNombre,_tmpMarca,_tmpTipo,_tmpFechaRegistro,_tmpDatosEspectrales,_tmpConductividad,_tmpTemperatura,_tmpAlcoholEstimado,_tmpNotas);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Integer> contarBebidas() {
    final String _sql = "SELECT COUNT(*) FROM bebidas";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bebidas"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Bebida>> buscar(final String query) {
    final String _sql = "SELECT * FROM bebidas WHERE nombre LIKE '%' || ? || '%' OR marca LIKE '%' || ? || '%' ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bebidas"}, new Callable<List<Bebida>>() {
      @Override
      @NonNull
      public List<Bebida> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfMarca = CursorUtil.getColumnIndexOrThrow(_cursor, "marca");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfFechaRegistro = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaRegistro");
          final int _cursorIndexOfDatosEspectrales = CursorUtil.getColumnIndexOrThrow(_cursor, "datosEspectrales");
          final int _cursorIndexOfConductividad = CursorUtil.getColumnIndexOrThrow(_cursor, "conductividad");
          final int _cursorIndexOfTemperatura = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatura");
          final int _cursorIndexOfAlcoholEstimado = CursorUtil.getColumnIndexOrThrow(_cursor, "alcoholEstimado");
          final int _cursorIndexOfNotas = CursorUtil.getColumnIndexOrThrow(_cursor, "notas");
          final List<Bebida> _result = new ArrayList<Bebida>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Bebida _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpMarca;
            _tmpMarca = _cursor.getString(_cursorIndexOfMarca);
            final String _tmpTipo;
            _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            final String _tmpFechaRegistro;
            _tmpFechaRegistro = _cursor.getString(_cursorIndexOfFechaRegistro);
            final String _tmpDatosEspectrales;
            _tmpDatosEspectrales = _cursor.getString(_cursorIndexOfDatosEspectrales);
            final float _tmpConductividad;
            _tmpConductividad = _cursor.getFloat(_cursorIndexOfConductividad);
            final float _tmpTemperatura;
            _tmpTemperatura = _cursor.getFloat(_cursorIndexOfTemperatura);
            final float _tmpAlcoholEstimado;
            _tmpAlcoholEstimado = _cursor.getFloat(_cursorIndexOfAlcoholEstimado);
            final String _tmpNotas;
            _tmpNotas = _cursor.getString(_cursorIndexOfNotas);
            _item = new Bebida(_tmpId,_tmpNombre,_tmpMarca,_tmpTipo,_tmpFechaRegistro,_tmpDatosEspectrales,_tmpConductividad,_tmpTemperatura,_tmpAlcoholEstimado,_tmpNotas);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Bebida>> obtenerPorTipo(final String tipo) {
    final String _sql = "SELECT * FROM bebidas WHERE tipo = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, tipo);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bebidas"}, new Callable<List<Bebida>>() {
      @Override
      @NonNull
      public List<Bebida> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfMarca = CursorUtil.getColumnIndexOrThrow(_cursor, "marca");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfFechaRegistro = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaRegistro");
          final int _cursorIndexOfDatosEspectrales = CursorUtil.getColumnIndexOrThrow(_cursor, "datosEspectrales");
          final int _cursorIndexOfConductividad = CursorUtil.getColumnIndexOrThrow(_cursor, "conductividad");
          final int _cursorIndexOfTemperatura = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatura");
          final int _cursorIndexOfAlcoholEstimado = CursorUtil.getColumnIndexOrThrow(_cursor, "alcoholEstimado");
          final int _cursorIndexOfNotas = CursorUtil.getColumnIndexOrThrow(_cursor, "notas");
          final List<Bebida> _result = new ArrayList<Bebida>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Bebida _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpMarca;
            _tmpMarca = _cursor.getString(_cursorIndexOfMarca);
            final String _tmpTipo;
            _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            final String _tmpFechaRegistro;
            _tmpFechaRegistro = _cursor.getString(_cursorIndexOfFechaRegistro);
            final String _tmpDatosEspectrales;
            _tmpDatosEspectrales = _cursor.getString(_cursorIndexOfDatosEspectrales);
            final float _tmpConductividad;
            _tmpConductividad = _cursor.getFloat(_cursorIndexOfConductividad);
            final float _tmpTemperatura;
            _tmpTemperatura = _cursor.getFloat(_cursorIndexOfTemperatura);
            final float _tmpAlcoholEstimado;
            _tmpAlcoholEstimado = _cursor.getFloat(_cursorIndexOfAlcoholEstimado);
            final String _tmpNotas;
            _tmpNotas = _cursor.getString(_cursorIndexOfNotas);
            _item = new Bebida(_tmpId,_tmpNombre,_tmpMarca,_tmpTipo,_tmpFechaRegistro,_tmpDatosEspectrales,_tmpConductividad,_tmpTemperatura,_tmpAlcoholEstimado,_tmpNotas);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
