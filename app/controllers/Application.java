package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import models.SearchParams;

import views.html.*;

public class Application extends Controller {

	static Form<SearchParams> searchForm = Form.form(SearchParams.class);

    public static Result index() {
      return redirect(routes.Application.search());
    }

		public static Result search() { 
			Form<SearchParams> filledForm = searchForm.bindFromRequest();
			if( filledForm.hasErrors()) {
				return badRequest( views.html.index.render( filledForm ));
			}
			else {
			return ok( views.html.index.render(searchForm));	
			}
		} 
}
