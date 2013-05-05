package models;
//import java.util.*;

import play.data.validation.Constraints.*;

public class SearchParams {
	public String zipCode;
	
	@Required
	public String businessName;

	@Required
	public String category;

}
