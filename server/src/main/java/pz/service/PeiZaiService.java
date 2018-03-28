package pz.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pz.dao.PeiZaiDao;
import pz.model.PeiZaiModel;

import java.util.*;

@Service
@Slf4j
public class PeiZaiService {

    private PeiZaiDao peiZaiDao;
    @Autowired
    public void setPeiZaiDao(PeiZaiDao peiZaiDao) {
        this.peiZaiDao = peiZaiDao;
    }


    @Autowired
    private PeiZaiModService peiZaiModService;


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


    @Transactional
    public boolean editOne(PeiZaiModel p) {
        PeiZaiModel pzm = peiZaiDao.findOne(p.getId());
        if (pzm != null) {
            boolean flag = peiZaiModService.save(pzm, "edit");
            if (flag) {
                PeiZaiModel editModel = peiZaiDao.saveAndFlush(p);
                return editModel != null;
            }else {
                return false;
            }
        }else {
            return false;
        }

    }

    public boolean addOne(PeiZaiModel p) {
        PeiZaiModel addModel = peiZaiDao.save(p);
        if (addModel != null) return true;
        return false;
    }

    public boolean deleteOne(Integer id) {
        PeiZaiModel pzm = peiZaiDao.findOne(id);
        if (pzm != null) {
            boolean flag = peiZaiModService.save(pzm, "delete");
            if (flag) {
                peiZaiDao.delete(id);
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
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

    // 获取最新修改的数据
    public HashMap<String, String> findRecentEditRecord() {
        PeiZaiModel pm = peiZaiDao.findFirstByOrderByModDateDesc();
        return pm.getMap();
    }

    public List<HashMap<String, String>> findAll() {
        List<HashMap<String, String>> lm = new ArrayList<>();
        List<PeiZaiModel> list = peiZaiDao.findAll();
        for (PeiZaiModel p : list) {
            lm.add(p.getMap());
        }
        return lm;
    }

    public List<HashMap<String, String>> findAllByModDate(Date start, Date end) {
        List<HashMap<String, String>> lm = new ArrayList<>();
        List<PeiZaiModel> list = peiZaiDao.findAllByModDateBetween(start, end);
        for (PeiZaiModel p : list) {
            lm.add(p.getMap());
        }
        return lm;
    }


    public ArrayList<HashMap<String, Object>> findAllListByAirlines(String airlines) {
        ArrayList<HashMap<String, Object>> lm = new ArrayList<>();
        List<PeiZaiModel> list = peiZaiDao.findAllByAirlines(airlines);
        for (PeiZaiModel p : list) {
            String type = p.getType();
            Integer index = checkType(lm, type);
            if (null != index) {
                ArrayList<HashMap<String, String>> al =
                        (ArrayList<HashMap<String, String>>) lm.get(index).get("REG");
                HashMap<String,String> m = new HashMap<>();
                m.put("PLANE_REG",p.getReg());
                al.add(m);
            }else{
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("PLANE_TYPE", type);
                ArrayList<HashMap<String, String>> al = new ArrayList<>();
                HashMap<String,String> m = new HashMap<>();
                m.put("PLANE_REG",p.getReg());
                al.add(m);
                temp.put("REG",al);
                lm.add(temp);
            }
        }
        lm.forEach((hashMap)->{
            hashMap.put("PLANE_REG_NUM",
                    ((ArrayList<HashMap<String,String>>)hashMap.get("REG")).size());
        });
        return lm;
     }


     private static Integer checkType(
             ArrayList<HashMap<String, Object>> lm, String type ) {
        Integer index = null;
        for (int i=0; i<lm.size(); i++) {
            if (type.equals(lm.get(i).get("PLANE_TYPE"))) {
                index = i;
            }
        }
        return  index;
     }

}
