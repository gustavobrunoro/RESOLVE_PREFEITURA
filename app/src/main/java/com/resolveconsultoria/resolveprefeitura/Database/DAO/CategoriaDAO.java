package com.resolveconsultoria.resolveprefeitura.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.resolveconsultoria.resolveprefeitura.Model.Categoria;

import java.util.List;

@Dao
public interface CategoriaDAO {

    @Query("SELECT * FROM Categoria")
    List<Categoria> getCategoriaList ();

    @Query("SELECT * FROM Categoria WHERE CategoriaID = :CategoriaID ")
    Categoria getCategoria (int CategoriaID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategoria (Categoria categoria);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategoriaList (List<Categoria> categorias);

    @Update
    void updateCategoria (Categoria categoria);

    @Query("DELETE FROM Categoria")
    void delete();

    @Query("DELETE FROM Categoria Where CategoriaID = :CategoriaID")
    void delete( int CategoriaID);

    @Query("SELECT * FROM Categoria WHERE CategoriaID = :CategoriaID")
    boolean isExist(int CategoriaID);

    @Query("SELECT * FROM Categoria WHERE CategoriaID = :CategoriaID AND Ativo = 1")
    boolean ativo(int CategoriaID);

}
