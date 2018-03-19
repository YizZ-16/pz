package pz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pz.model.PeiZaiModel;


public interface PeiZaiDao
        extends JpaRepository<PeiZaiModel, Integer>{

    Page<PeiZaiModel> findAllByAirlines(String airlines, Pageable page);

    Page<PeiZaiModel> findAllByReg(String reg, Pageable page);

    Page<PeiZaiModel> findAllByAirlinesAndReg(String airlines, String reg, Pageable pageable);

    //Iterable<PeiZaiModel> findAllBy
}
