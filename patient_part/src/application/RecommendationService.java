package application;

import java.sql.SQLException;
import java.util.List;

public class RecommendationService {
    private UserDAO userDAO;

    public RecommendationService() {
        this.userDAO = new UserDAO();
    }

    public Recommendation getRecommendation(List<String> symptoms) throws SQLException {
        return userDAO.getRecommendation(symptoms);
    }
}
