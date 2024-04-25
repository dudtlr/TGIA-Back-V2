package capstone.market.repository;

import capstone.market.domain.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
public interface PostDocumentRepository extends ElasticsearchRepository<PostDocument, Long> {

}