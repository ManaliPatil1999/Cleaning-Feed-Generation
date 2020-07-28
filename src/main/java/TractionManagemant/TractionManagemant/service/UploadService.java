package TractionManagemant.TractionManagemant.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import TractionManagemant.TractionManagemant.model.UploadModel;
import TractionManagemant.TractionManagemant.repository.UploadRepository;

@Service
public class UploadService {
	
	@Autowired
	private UploadRepository uploadRepository;
	
	
	
	public List<UploadModel> getFeedGenerated(String feedgenerated){
		List<UploadModel> list = uploadRepository.findByFeedgenerated(feedgenerated);
		return list.stream().map(file -> {
			UploadModel um = new UploadModel();
			um.setAmount(um.getAmount());
			um.setDate(um.getDate());
			um.setFeedgenerated("true");
			um.setPayee(um.getPayee());
			um.setPayer(um.getPayer());
			um.setStatus(um.getStatus());
			um.setTransactionRef(um.getTransactionRef());
			return um;
		}).collect(Collectors.toList());
		
				
	}

}
