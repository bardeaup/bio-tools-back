package com.biotools.rest;

import com.biotools.as.CellCountExperimentAS;
import com.biotools.dto.CellularCountConditonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/cellular-count-condition")
public class CellularCountConditionRestController {

    @Autowired
    CellCountExperimentAS cellCountExperimentAS;

    private static final Logger logger = LoggerFactory.getLogger(CellularCountConditionRestController.class);

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CellularCountConditonDTO> saveCellCount(@RequestBody CellularCountConditonDTO cellCountConditionDTO){
        logger.info("Class : {} ; Call endpoint POST saveCellCount", CellularCountConditionRestController.class.getName());
        return new ResponseEntity<>(cellCountExperimentAS.saveCellCount(cellCountConditionDTO), HttpStatus.OK);
    }
}
