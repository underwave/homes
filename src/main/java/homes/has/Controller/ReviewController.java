package homes.has.Controller;

import homes.has.repository.BuildingRepository;
import homes.has.repository.ReviewRepository;
import homes.has.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController{
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewService service;
    @Autowired
    private BuildingRepository buildingRepository;




}
