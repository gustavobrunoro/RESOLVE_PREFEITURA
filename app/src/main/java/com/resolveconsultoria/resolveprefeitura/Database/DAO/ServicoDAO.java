package com.resolveconsultoria.resolveprefeitura.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.resolveconsultoria.resolveprefeitura.Model.Categoria;
import com.resolveconsultoria.resolveprefeitura.Model.Servico;

import java.util.List;

@Dao
public interface ServicoDAO {

    @Query("SELECT * FROM Servico")
    List<Servico> getServicoList ();

    @Query("SELECT * FROM Servico WHERE ServicoID = :ServicoID ")
    Servico getServico (int ServicoID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertServico (Servico servico);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertServicoList (List<Servico> servicos);

    @Update
    void updateServico (Servico servico);

    @Query("DELETE FROM Servico")
    void delete();

    @Query("DELETE FROM Servico Where ServicoID = :ServicoID")
    void delete( int ServicoID);

    @Query("SELECT * FROM Servico WHERE ServicoID = :ServicoID")
    boolean isExist(int ServicoID);
}
