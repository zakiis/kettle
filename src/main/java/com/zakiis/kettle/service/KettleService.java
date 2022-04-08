package com.zakiis.kettle.service;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KettleService {
	
	@Autowired
	KettleDatabaseRepository repository;
	
	public void runTransfer(String transformName) throws KettleException {
		RepositoryDirectoryInterface directoryTree = repository.loadRepositoryDirectoryTree();
		TransMeta transMeta = repository.loadTransformation(transformName, directoryTree, null, true, null);
		Trans trans=new Trans(transMeta); 
        trans.execute(null); 
        trans.waitUntilFinished();//等待直到数据结束 
        if(trans.getErrors()>0){ 
            System.out.println("transformation error"); 
        }else{ 
            System.out.println("transformation successfully"); 
        } 
	}
}
