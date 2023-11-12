package com.example.waterSports.repo;

import com.example.waterSports.modal.OrderParasailing;
import com.example.waterSports.modal.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderParasalingRepo extends JpaRepository<OrderParasailing, Long>
{
    List<OrderParasailing> findByDateBetweenOrderByBillNoDesc(LocalDate startDate, LocalDate endDate);
    OrderParasailing findTopByOrderByBillNoDesc();

    @Query("SELECT new com.example.waterSports.modal.Report(EXTRACT(DAY FROM date), null, null, sum(rate * nPerson)) FROM OrderParasailing\n" +
            "where date between :dateFrom and :dateTo group by date")
    List<Report> getDailyReportWaterSport(LocalDate dateFrom, LocalDate dateTo);

    @Query("SELECT new com.example.waterSports.modal.Report(null, EXTRACT(MONTH FROM date), EXTRACT(YEAR FROM date), sum(rate * nPerson)) FROM OrderParasailing\n" +
            "where date between :dateFrom and :dateTo group by EXTRACT(MONTH from date), EXTRACT(YEAR from date)")
    List<Report> getMonthlyReportWaterSport(LocalDate dateFrom, LocalDate dateTo);

    @Query("SELECT new com.example.waterSports.modal.Report(null, null, EXTRACT(YEAR FROM date), sum(rate * nPerson)) FROM OrderParasailing\n" +
            "where date between :dateFrom and :dateTo group by EXTRACT(YEAR from date)")
    List<Report> getYearlyReportWaterSport(LocalDate dateFrom, LocalDate dateTo);

}
