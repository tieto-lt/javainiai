package lt.tieto.msi2016.operator.repository;

import com.nurkiewicz.jdbcrepository.RowUnmapper;
import lt.tieto.msi2016.operator.OperatorStatus;
import lt.tieto.msi2016.operator.repository.model.OperatorDb;
import lt.tieto.msi2016.utils.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by it11 on 16.8.8.
 */



@Repository
public class OperatorVerification extends BaseRepository <OperatorDb > {


    public static final String SELECT_OPERATOR_TOKEN = "SELECT * FROM operator_verification where token = ?";
    public static final String SELECT_OPERATOR_USER_ID = "SELECT * FROM operator_verification where user_id = ?";
    @Autowired
    private JdbcTemplate template;



    private static final RowMapper<OperatorDb > ROW_MAPPER = (rs, rowNum) -> {
        OperatorDb  item = new OperatorDb ();
        item.setId(rs.getLong("id"));
        item.setToken(rs.getString("token"));
        item.setUserId(rs.getLong("user_id"));
        item.setStatus(OperatorStatus.Status.valueOf(rs.getString("status")));
        return item;
    };

    private static final RowUnmapper<OperatorDb > ROW_UNMAPPER = operatorDb  -> mapOf(
            "id", operatorDb.getId(),
            "status", operatorDb.getStatus().toString(),
            "token", operatorDb.getToken(),
            "user_id", operatorDb.getUserId()
    );

    public OperatorVerification() {
        super(ROW_MAPPER, ROW_UNMAPPER, "operator_verification", "id");
    }

    public OperatorDb operatorByToken(String token) {
        List<OperatorDb> list = template.query(SELECT_OPERATOR_TOKEN, new Object[]{token}, ROW_MAPPER);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public OperatorDb operatorByUserID(Long user_id) {
        List <OperatorDb> selectedOperators = template.query(SELECT_OPERATOR_USER_ID, new Object[] {user_id} , ROW_MAPPER);
        if(!selectedOperators.isEmpty()){
            return selectedOperators.get(0);
        }
        return null;
    }


}