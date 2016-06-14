package cn.ccsgroup.ccsframework.sysparamManager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsgroup.ccsframework.entity.Annex;
import cn.ccsgroup.ccsframework.entity.Information;
import cn.ccsgroup.ccsframework.sysparamManager.dao.InformationDao;
import cn.ccsgroup.ccsframework.sysparamManager.service.InformationService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

@Service("InformationService")
@Transactional
public class InformationServiceImpl implements InformationService{

	@Resource(name="InformationDao")
	private InformationDao informationDao;
	public void setInformationDao(InformationDao informationDao) {
		this.informationDao = informationDao;
	}
	
	@Override
	public EasyUIPage getInformationList(EasyUIPage page) {
		page.setRows(informationDao.queryInformations(page));
		page.setTotal(informationDao.queryInformationsCounts());
		return page;
	}

	@Override
	public Integer saveInformation(Information information,String[] appids) {
		int id = informationDao.addInformations(information);
		informationDao.addInformationmapping(id,appids,information.getCreateruser());
		return id;
	}

	@Override
	public boolean deleteInformation(int id) {
		return informationDao.deleteInformation(id);
	}

	@Override
	public List getSendAppList(Integer infoid) {
		return informationDao.getSendAppList(infoid);
	}

	@Override
	public void saveAnnex(List<Annex> annexList) {
		if(annexList.size() > 0){
			informationDao.addAnnex(annexList);
		}
	}

	@Override
	public List getAppList() {
		return informationDao.getAppList();
	}
	@Override
	public Information getInformationsById(Integer id) {
		return informationDao.getInformationById(id);
	}

	@Override
	public List<Annex> getAnnextListById(Integer id) {
		return informationDao.getAnnexByInfoid(id);
	}

	@Override
	public Integer saveInformationMapping(Integer id, String[] appids,String updateUser) {
		informationDao.deleteInformationmapping(id);
		informationDao.addInformationmapping(id,appids,updateUser);
		return null;
	}

	@Override
	public boolean updateInformation(Information information) {
		return informationDao.updateInformation(information);
	}

	@Override
	public boolean deleteAnnex(List<Annex> annexList) {
		for(int i = 0 ; i < annexList.size() ; i++){
			informationDao.deleteAnnex(annexList.get(i).getId());
		}
		return true;
	}

	@Override
	public List<Information> queryNoticeList(Integer[] sysid) {
		
		return informationDao.getInformationListBySentSys(sysid);
	}

	@Override
	public Annex queryAnnexById(Integer id) {
		return informationDao.getAnnexById(id);
	}
}
