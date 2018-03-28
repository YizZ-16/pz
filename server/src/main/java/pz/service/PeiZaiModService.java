package pz.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pz.dao.PeiZaiModDao;
import pz.model.PeiZaiModModel;
import pz.model.PeiZaiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PeiZaiModService {

    @Autowired
    private PeiZaiModDao peiZaiModDao;

    boolean save(PeiZaiModel pz, String operation) {
        PeiZaiModModel pzm = new PeiZaiModModel(pz);
        pzm.setOperation(operation);
        PeiZaiModModel pzm1 = peiZaiModDao.save(pzm);
        return pzm1 != null;
    }

    public ArrayList<HashMap<String, String>> findByPlaneId(Integer planeId) {
        List<PeiZaiModModel> list = peiZaiModDao.findAllByPlaneId(planeId);
        if (list.isEmpty()) {
            return null;
        }
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        for (PeiZaiModModel p : list) {
            al.add(p.getMap());
        }
        return al;
    }
}
