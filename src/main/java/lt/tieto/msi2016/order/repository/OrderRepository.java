package lt.tieto.msi2016.order.repository;

import com.nurkiewicz.jdbcrepository.RowUnmapper;
import lt.tieto.msi2016.order.repository.model.OrderDb;
import lt.tieto.msi2016.utils.repository.BaseRepository;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class OrderRepository extends BaseRepository<OrderDb> {

    private static final RowMapper<OrderDb> ROW_MAPPER = (rs, rowNum) -> {
        OrderDb order = new OrderDb();
        order.setId(rs.getLong("id"));
        order.setCreatedBy(rs.getLong("created_by"));
        order.setMissionName(rs.getString("mission_name"));
        order.setFullName(rs.getString("full_name"));
        order.setPhone(rs.getString("phone"));
        order.setEmail(rs.getString("email"));
        order.setSubmissionDate(new DateTime(rs.getTimestamp("submission_date")));
        order.setDetails(rs.getString("details"));
        order.setOrderState(OrderDb.OrderState.valueOf(rs.getString("order_state")));

        return order;
    };

    private static final RowUnmapper<OrderDb> ROW_UNMAPPER = orderDb -> mapOf(
            "id", orderDb.getId(),
            "created_by", orderDb.getCreatedBy(),
            "mission_name", orderDb.getMissionName(),
            "full_name", orderDb.getFullName(),
            "phone", orderDb.getPhone(),
            "email", orderDb.getEmail(),
            "submission_date", new Timestamp(orderDb.getSubmissionDate().getMillis()),
            "details", orderDb.getDetails(),
            "order_state", orderDb.getOrderState().toString()

    );

    public OrderRepository() {
        super(ROW_MAPPER, ROW_UNMAPPER, "orders", "id");
    }
}