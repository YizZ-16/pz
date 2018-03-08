package pz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pz.dao.PeiZaiDao;
import pz.model.PeiZaiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PeiZaiService {

    private PeiZaiDao peiZaiDao;
    @Autowired
    public void setPeiZaiDao(PeiZaiDao peiZaiDao) {
        this.peiZaiDao = peiZaiDao;
    }


    public HashMap<String, Object> findAllByAirlines(String airlines, Integer pageIndex, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        Page<PeiZaiModel> page = peiZaiDao.findAllByAirlines(airlines, new PageRequest(pageIndex, pageSize));
        //map.put("TOTAL_PAGES", page.getTotalPages());
        map.put("TOTAL_ELEMENTS", page.getTotalElements());
        List<PeiZaiModel> list= page.getContent();
        List<HashMap<String, String>> lm = new ArrayList<>();
        for (PeiZaiModel p : list) {
            lm.add(p.getMap());
        }
        map.put("DATA_LIST", lm);
        return map;
    }


    public boolean editOne(PeiZaiModel p) {
       PeiZaiModel editModel = peiZaiDao.saveAndFlush(p);
       if (editModel != null) return  true;
       return  false;
    }

    public boolean addOne(PeiZaiModel p) {
        PeiZaiModel addModel = peiZaiDao.save(p);
        if (addModel != null) return true;
        return false;
    }

    public boolean deleteOne(Integer id) {
        peiZaiDao.delete(id);
        return true;
    }

    public HashMap<String, Object> findAll(Integer pageIndex, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        Page<PeiZaiModel> page = peiZaiDao.findAll(new PageRequest(pageIndex, pageSize));
        //map.put("TOTAL_PAGES", page.getTotalPages());
        map.put("TOTAL_ELEMENTS", page.getTotalElements());
        List<PeiZaiModel> list= page.getContent();
        List<HashMap<String, String>> lm = new ArrayList<>();
        for (PeiZaiModel p : list) {
            lm.add(p.getMap());
        }
        map.put("DATA_LIST", lm);
        return map;
    }

    public HashMap<String, Object> findAllByReg(String reg, Integer pageIndex, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        Page<PeiZaiModel> page = peiZaiDao.findAllByReg(reg, new PageRequest(pageIndex, pageSize));
        //map.put("TOTAL_PAGES", page.getTotalPages());
        map.put("TOTAL_ELEMENTS", page.getTotalElements());
        List<PeiZaiModel> list= page.getContent();
        List<HashMap<String, String>> lm = new ArrayList<>();
        for (PeiZaiModel p : list) {
            lm.add(p.getMap());
        }
        map.put("DATA_LIST", lm);
        return map;
    }

    public HashMap<String, Object> findAllByAirlinesAndReg(
            String airlines, String reg, Integer pageIndex, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        Page<PeiZaiModel> page = peiZaiDao.findAllByAirlinesAndReg(airlines, reg, new PageRequest(pageIndex, pageSize));
        map.put("TOTAL_ELEMENTS", page.getTotalElements());
        List<PeiZaiModel> list= page.getContent();
        List<HashMap<String, String>> lm = new ArrayList<>();
        for (PeiZaiModel p : list) {
            lm.add(p.getMap());
        }
        map.put("DATA_LIST", lm);
        return map;
    }
}
