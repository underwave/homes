package homes.has.service;

import homes.has.domain.Building;
import homes.has.domain.Review;
import homes.has.repository.BuildingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public Building save(String location, double posX, double posY){
    Building building = buildingRepository.findByLocation(location);
        if (building == null) {//빌딩 테이블에 location이 존재하지 않으면 추가
        building = new Building(location,posX,posY);
        }

        return buildingRepository.save(building);
    }

    public Building findByLocation(String location){
        return buildingRepository.findByLocation(location);
    }
    public List<Building> findByPosxBetweenAndPosyBetween(double minx, double maxx, double miny, double maxy){
        return buildingRepository.findByPosxBetweenAndPosyBetween(minx, maxx, miny, maxy);
    }


}
