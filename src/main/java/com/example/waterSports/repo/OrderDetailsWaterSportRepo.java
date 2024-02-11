package com.example.waterSports.repo;

import com.example.waterSports.modal.OrderDetailsWaterSport;
import com.example.waterSports.modal.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderDetailsWaterSportRepo extends JpaRepository<OrderDetailsWaterSport, Long>
{
    List<OrderDetailsWaterSport> findByBillNo(Long billNo);

    @Modifying
    @Transactional
    @Query("delete from OrderDetailsWaterSport where billNo = :billNo")
    void deleteByBillNo(Long billNo);
    OrderDetailsWaterSport findByBillNoAndIdActivity(Long billNo, Integer idActivity);

    @Query("SELECT new com.example.waterSports.modal.Report(EXTRACT(DAY FROM o.date), EXTRACT(MONTH FROM o.date), EXTRACT(YEAR FROM o.date), sum(od.rate * od.persons), o.idRef) FROM OrderWaterSport o\n" +
            "inner join OrderDetailsWaterSport od\n" +
            "on o.billNo = od.billNo\n" +
            "where o.date between :dateFrom and :dateTo group by o.date")
    List<Report> getDailyReportWaterSport(LocalDate dateFrom, LocalDate dateTo);

    @Query("SELECT new com.example.waterSports.modal.Report(0, EXTRACT(MONTH FROM o.date), EXTRACT(YEAR FROM o.date), sum(od.rate * od.persons), o.idRef) FROM OrderWaterSport o\n" +
            "inner join OrderDetailsWaterSport od\n" +
            "on o.billNo = od.billNo\n" +
            "where o.date between :dateFrom and :dateTo group by EXTRACT(MONTH from o.date), EXTRACT(YEAR from o.date)")
    List<Report> getMonthlyReportWaterSport(LocalDate dateFrom, LocalDate dateTo);

    @Query("SELECT new com.example.waterSports.modal.Report(0, 0, EXTRACT(YEAR FROM o.date), sum(od.rate * od.persons), o.idRef) FROM OrderWaterSport o\n" +
            "inner join OrderDetailsWaterSport od\n" +
            "on o.billNo = od.billNo\n" +
            "where o.date between :dateFrom and :dateTo group by EXTRACT(YEAR from o.date)")
    List<Report> getYearlyReportWaterSport(LocalDate dateFrom, LocalDate dateTo);

    @Query("SELECT new com.example.waterSports.modal.Report(0, 0, 0, sum(od.rate * od.persons), r.idOwner) FROM OrderWaterSport o inner join Referee r on o.idRef = r.id\n" +
            "inner join OrderDetailsWaterSport od\n" +
            "on o.billNo = od.billNo\n" +
            "where o.date between :dateFrom and :dateTo group by r.idOwner")
    List<Report> getReportReferee(LocalDate dateFrom, LocalDate dateTo);

    @Query("select sum(if(od.persons is null or cast(od.persons as text)='', 0, od.persons)) from OrderDetailsWaterSport od " +
            "inner join OrderWaterSport o on od.billNo = o.billNo " +
            "inner join Referee r on o.idRef = r.id " +
            "where o.date between :dateFrom and :dateTo and r.idOwner = :idOwner and od.idActivity = :idActivity group by r.idOwner")
    Integer countPerson(Long idOwner, Integer idActivity, LocalDate dateFrom, LocalDate dateTo);
}
