package com.drinksafe.manager.data.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.drinksafe.manager.data.models.Perfil;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PerfilDao_Impl implements PerfilDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Perfil> __insertionAdapterOfPerfil;

  public PerfilDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPerfil = new EntityInsertionAdapter<Perfil>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `perfil` (`id`,`nombreUsuario`,`syncCode`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Perfil entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getNombreUsuario());
        statement.bindString(3, entity.getSyncCode());
      }
    };
  }

  @Override
  public Object insertarOActualizar(final Perfil perfil,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPerfil.insert(perfil);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Perfil> obtenerPerfil() {
    final String _sql = "SELECT * FROM perfil WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"perfil"}, new Callable<Perfil>() {
      @Override
      @Nullable
      public Perfil call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombreUsuario = CursorUtil.getColumnIndexOrThrow(_cursor, "nombreUsuario");
          final int _cursorIndexOfSyncCode = CursorUtil.getColumnIndexOrThrow(_cursor, "syncCode");
          final Perfil _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombreUsuario;
            _tmpNombreUsuario = _cursor.getString(_cursorIndexOfNombreUsuario);
            final String _tmpSyncCode;
            _tmpSyncCode = _cursor.getString(_cursorIndexOfSyncCode);
            _result = new Perfil(_tmpId,_tmpNombreUsuario,_tmpSyncCode);
          } else {
            _result = null;
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
