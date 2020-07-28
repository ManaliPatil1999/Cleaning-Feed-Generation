package TractionManagemant.TractionManagemant.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import TractionManagemant.TractionManagemant.model.*;

@Repository
public interface UploadRepository extends JpaRepository<UploadModel, Long>{

	List<UploadModel> findByStatus(String status);
	
	List< UploadModel> findByFeedgenerated(String feedgenerated);
	
	UploadModel findByTransactionRef(String transactionRef);

	//String setDate(String date1);

}
