package pz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pz.model.PeiZaiModModel;
import pz.model.PeiZaiModel;

import java.util.List;

public interface PeiZaiModDao extends
        JpaRepository<PeiZaiModModel, Integer>{

    List<PeiZaiModModel> findAllByPlaneId(Integer planeId);
//    SELECT TOP 1
//            [PLANE_ID]
//            ,[PLANE_REG]
//            ,[PLANE_TYPE]
//            ,[PLANE_BUJU]
//            ,[PLANE_CND]
//            ,[PLANE_AIRLINES]
//            ,[PLANE_BOW]
//            ,[PLANE_BOI]
//            ,[PLANE_MZFW]
//            ,[PLANE_MZDW]
//            ,[PLANE_MTOW]
//            ,[PLANE_UPDATE_DATE]
//            ,[PLANE_INSERT_DATE]
//            ,[PLANE_MOD_DATE]
//    FROM [PZDB].[dbo].[PZ_PLANE_STATIC]
//    ORDER BY [PLANE_MOD_DATE] DESC;


//    @Query(value = "SELECT TOP 1 * FROM PZ_PLANE_STATIC ORDER BY PLANE_MOD_DATE DESC")
//    PeiZaiModel findMaxModDate();

}
