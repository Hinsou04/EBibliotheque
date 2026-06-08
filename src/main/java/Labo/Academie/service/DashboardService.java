package Labo.Academie.service;

import Labo.Academie.dto.DashboardResponse;
import org.springframework.stereotype.Service;

@Service
public interface DashboardService {
    DashboardResponse getDashboardStats();
}
