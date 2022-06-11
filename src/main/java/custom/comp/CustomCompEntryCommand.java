package custom.comp;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.kind.database.CustomKindDAO;
import custom.kind.database.CustomKindVO;

public class CustomCompEntryCommand implements CustomCompInterface {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//기업고객고분코드 목록조회 
		CustomKindDAO dao = new CustomKindDAO();
		List<CustomKindVO> customKind = dao.searchCustomCompCustomKindList();
		request.setAttribute("customKind", customKind);
	}
}