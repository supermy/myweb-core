package org.supermy.core.listener;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.supermy.core.domain.BaseDomain;

/**
 * 在自动为entity添加审计信息的Hibernate EventListener.
 * 
 * 在hibernate执行saveOrUpdate()时,自动为AuditableEntity的子类添加审计信息.
 * 
 */
@SuppressWarnings("serial")
public class AuditListener implements SaveOrUpdateEventListener {
	Log log = LogFactory.getLog(AuditListener.class);

	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
		log.debug("audit listen save or update domain change create date and update date ......");
		Object e = event.getEntity();		
		Object object = event.getObject();

		//如果对象是AuditableEntity子类,添加审计信息.
		if (object instanceof BaseDomain) {
			BaseDomain entity = (BaseDomain) object;
			if ( null==entity.getPkId() || entity.getPkId()==0 || entity.getPkId()==-1 ) {
				//创建新对象
				entity.setCreateDate(new Date());
				entity.setUpdateDate(new Date());
//				entity.setCreateBy(SecurityUtils.getCurrentUserName());
				entity.setCreateBy("testuser");
				entity.setUpdateBy("testuser");
				entity.setEnabled(true);
			} else {
				//修改旧对象
				entity.setUpdateDate(new Date());
//				entity.setUpdateBy(SecurityUtils.getCurrentUserName());
				entity.setUpdateBy("testuser");
			}
		}
		
	}
}
