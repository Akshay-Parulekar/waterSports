package com.example.waterSports.repo;

import com.example.waterSports.modal.OrderParasailing;
import com.example.waterSports.modal.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderParasalingRepo extends JpaRepository<OrderParasailing, Long>
{
    OrderParasailing getByBillNo(Long billNo);

    List<OrderParasailing> findByDateBetweenOrderByBillNoDesc(LocalDateTime startDate, LocalDateTime endDate);
    List<OrderParasailing> findByDateBetweenOrderByBillNo(LocalDateTime startDate, LocalDateTime endDate);

    @Query("from OrderParasailing where date between :startDate and :endDate and idRef in (select id from Referee where idOwner = :idOwner) order by date, idRef")
    List<OrderParasailing> findReferences(Long idOwner, LocalDateTime startDate, LocalDateTime endDate);

    OrderParasailing findTopByOrderByBillNoDesc();

    @Query("SELECT new com.example.waterSports.modal.Report(EXTRACT(DAY FROM date), EXTRACT(MONTH FROM date), EXTRACT(YEAR FROM date), sum(rate * nPerson), 0L) FROM OrderParasailing\n" +
            "where date between :dateFrom and :dateTo group by date")
    List<Report> getDailyReportWaterSport(LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("SELECT new com.example.waterSports.modal.Report(EXTRACT(DAY FROM date), EXTRACT(MONTH FROM date), EXTRACT(YEAR FROM date), sum(rate * nPerson), 0L) FROM OrderParasailing\n" +
            "where date between :dateFrom and :dateTo group by EXTRACT(MONTH from date), EXTRACT(YEAR from date)")
    List<Report> getMonthlyReportWaterSport(LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("SELECT new com.example.waterSports.modal.Report(EXTRACT(DAY FROM date), EXTRACT(MONTH FROM date), EXTRACT(YEAR FROM date), sum(rate * nPerson), 0L) FROM OrderParasailing\n" +
            "where date between :dateFrom and :dateTo group by EXTRACT(YEAR from date)")
    List<Report> getYearlyReportWaterSport(LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("SELECT new com.example.waterSports.modal.Report(0, 0, 0, sum(o.rate * o.nPerson), r.idOwner) FROM OrderParasailing o inner join Referee r on o.idRef = r.id\n" +
            "where date between :dateFrom and :dateTo group by r.idOwner")
    List<Report> getReportReferee(LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("SELECT sum(if(o.nPerson is null or cast(o.nPerson as text)='', 0, o.nPerson)) FROM OrderParasailing o inner join Referee r on o.idRef = r.id\n" +
            "where date between :dateFrom and :dateTo and r.idOwner = :idOwner group by r.idOwner")
    Integer countPerson(Long idOwner, LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select count(id) from OrderParasailing where idRef = :idRef")
    Integer countReferences(Long idRef);

    @Query("select if(sum(nPerson) is null or cast(sum(nPerson) as text)='', 0, sum(nPerson)) from OrderParasailing where idRef = :idRef and date between :dateFrom and :dateTo")
    Integer getQty(Long idRef, LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select if(sum(nPerson * rate) is null or cast(sum(nPerson * rate) as text)='', 0, sum(nPerson * rate)) from OrderParasailing where idRef = :idRef and date between :dateFrom and :dateTo")
    Integer getAmount(Long idRef, LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select if(sum(nPerson) is null or cast(sum(nPerson) as text)='', 0, sum(nPerson)) from OrderParasailing where idRef in (select id from Referee where idOwner = :idRef) and date between :dateFrom and :dateTo")
    Integer getQtyOwner(Long idRef, LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select if(sum(nPerson * rate) is null or cast(sum(nPerson * rate) as text)='', 0, sum(nPerson * rate)) from OrderParasailing where idRef in (select id from Referee where idOwner = :idRef) and date between :dateFrom and :dateTo")
    Integer getAmountOwner(Long idRef, LocalDateTime dateFrom, LocalDateTime dateTo);
}
