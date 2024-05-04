package com.example.waterSports.repo;

import com.example.waterSports.modal.OrderDetailsWaterSport;
import com.example.waterSports.modal.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
            "where o.date between :dateFrom and :dateTo group by function('DATE', o.date)")
    List<Report> getDailyReportWaterSport(LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("SELECT new com.example.waterSports.modal.Report(0, EXTRACT(MONTH FROM o.date), EXTRACT(YEAR FROM o.date), sum(od.rate * od.persons), o.idRef) FROM OrderWaterSport o\n" +
            "inner join OrderDetailsWaterSport od\n" +
            "on o.billNo = od.billNo\n" +
            "where o.date between :dateFrom and :dateTo group by EXTRACT(MONTH from o.date), EXTRACT(YEAR from o.date)")
    List<Report> getMonthlyReportWaterSport(LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("SELECT new com.example.waterSports.modal.Report(0, 0, EXTRACT(YEAR FROM o.date), sum(od.rate * od.persons), o.idRef) FROM OrderWaterSport o\n" +
            "inner join OrderDetailsWaterSport od\n" +
            "on o.billNo = od.billNo\n" +
            "where o.date between :dateFrom and :dateTo group by EXTRACT(YEAR from o.date)")
    List<Report> getYearlyReportWaterSport(LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select sum(if(od.persons is null or cast(od.persons as text)='', 0, od.persons)) from OrderDetailsWaterSport od " +
            "inner join OrderWaterSport o on od.billNo = o.billNo " +
            "inner join Referee r on o.idRef = r.id " +
            "where o.date between :dateFrom and :dateTo and r.idOwner = :idOwner and od.idActivity = :idActivity group by r.idOwner")
    Integer countPerson(Long idOwner, Integer idActivity, LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("from OrderDetailsWaterSport where billNo in (select billNo from OrderWaterSport where date between :dateFrom and :dateTo) order by billNo")
    List<OrderDetailsWaterSport> findByDateBetweenOrderByBillNo(LocalDateTime dateFrom, LocalDateTime dateTo);

    // for marketers

    @Query("select if(sum(persons) is null or cast(sum(persons) as text)='', 0, sum(persons)) from OrderDetailsWaterSport where billNo = :billNo and idActivity = 1")
    Integer getQtyAllRides(Long billNo);

    @Query("select if(sum(persons) is null or cast(sum(persons) as text)='', 0, sum(persons)) from OrderDetailsWaterSport where billNo = :billNo and idActivity != 1")
    Integer getQtyExtraRides(Long billNo);

    @Query("select if(sum(persons) is null or cast(sum(persons) as text)='', 0, sum(persons)) from OrderDetailsWaterSport where idActivity = 1 and billNo in (select billNo from OrderWaterSport where idRef = :idRef and date between :dateFrom and :dateTo)")
    Integer getQtyAllRides(Long idRef, LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select if(sum(persons) is null or cast(sum(persons) as text)='', 0, sum(persons)) from OrderDetailsWaterSport where idActivity != 1 and billNo in (select billNo from OrderWaterSport where idRef = :idRef and date between :dateFrom and :dateTo)")
    Integer getQtyExtraRides(Long idRef, LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select if(sum(persons * rate) is null or cast(sum(persons * rate) as text)='', 0, sum(persons * rate)) from OrderDetailsWaterSport where idActivity = 1 and billNo in (select billNo from OrderWaterSport where idRef = :idRef and date between :dateFrom and :dateTo)")
    Integer getAmountAllRides(Long idRef, LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select if(sum(persons * rate) is null or cast(sum(persons * rate) as text)='', 0, sum(persons * rate)) from OrderDetailsWaterSport where idActivity != 1 and billNo in (select billNo from OrderWaterSport where idRef = :idRef and date between :dateFrom and :dateTo)")
    Integer getAmountExtraRides(Long idRef, LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select if(sum(persons * rate) is null or cast(sum(persons * rate) as text)='', 0, sum(persons * rate)) from OrderDetailsWaterSport where billNo = :billNo")
    Integer getAmount(Long billNo);

    // for owners

    @Query("select if(sum(persons) is null or cast(sum(persons) as text)='', 0, sum(persons)) from OrderDetailsWaterSport where idActivity = 1 and billNo in (select billNo from OrderWaterSport where idRef in (select id from Referee where idOwner = :idRef) and date between :dateFrom and :dateTo)")
    Integer getQtyAllRidesOwner(Long idRef, LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select if(sum(persons) is null or cast(sum(persons) as text)='', 0, sum(persons)) from OrderDetailsWaterSport where idActivity != 1 and billNo in (select billNo from OrderWaterSport where idRef in (select id from Referee where idOwner = :idRef) and date between :dateFrom and :dateTo)")
    Integer getQtyExtraRidesOwner(Long idRef, LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select if(sum(persons * rate) is null or cast(sum(persons * rate) as text)='', 0, sum(persons * rate)) from OrderDetailsWaterSport where idActivity = 1 and billNo in (select billNo from OrderWaterSport where idRef in (select id from Referee where idOwner = :idRef) and date between :dateFrom and :dateTo)")
    Integer getAmountAllRidesOwner(Long idRef, LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select if(sum(persons * rate) is null or cast(sum(persons * rate) as text)='', 0, sum(persons * rate)) from OrderDetailsWaterSport where idActivity != 1 and billNo in (select billNo from OrderWaterSport where idRef in (select id from Referee where idOwner = :idRef) and date between :dateFrom and :dateTo)")
    Integer getAmountExtraRidesOwner(Long idRef, LocalDateTime dateFrom, LocalDateTime dateTo);

}
