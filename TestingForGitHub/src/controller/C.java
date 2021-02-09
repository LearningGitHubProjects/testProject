package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.Constants;
import com.sun.xml.internal.ws.util.StringUtils;

import emailnotification.EmailEndpoint;
import emailnotification.MiscUtils;
import model.CityDetails;
import model.CollegeCourseMapping;
import model.CollegeDetails;
import model.CollegeStreamMapping;
import model.ContactDetails;
import model.CounsellorEducationalBackground;
import model.CounsellorPreferences;
import model.CounsellorProfileDetails;
import model.CounsellorWorkExperience;
import model.CourseDetails;
import model.EduBgDetails;
import model.EduPrefDetails;
import model.EduStudentDao;
import model.FeaturedColleges;
import model.HomePageData;
import model.InstituteLeads;
import model.InstituteRegistrationDetails;
import model.InstituteReviews;
import model.MonthlyEnquiryChartData;
import model.PersonalInfoDetails;
import model.QAForumDetails;
import model.RegisteredInstituteDetails;
import model.SpecificCourseInfo;
import model.SpecificStreamInfo;
import model.StreamDetails;
import model.StudentDetails;
import model.UserDetails;
import model.WorkExperienceDetails;
import operation.EduDao;
import operation.PrepareBean;
import service.EduCounsellorService;
import service.EduInstituteService;
import service.EduService;
import service.EduStudentService;
import utils.CaseConstants;
import utils.EduKeyValue;
import utils.EduUtils;
import utils.PageConstants;
import utils.SmValidator;
import utils.Validations;

@WebServlet("/C")
public class C extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, sdnsldjkfngvuijlnsd lfkbnikjsdnhbfgvbj nskjdfvjn sd,mfnbijhbresponse);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("This is Github testing");
		processRequest(request, response);
		
		System.out.println("THESE CHANGES ARE MADE BY PRADIP SAWANT.......");
	}
	
	

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
		System.out.println("Request processing start...");
		UserDetails userDetails = new UserDetails();
		StudentDetails studentDetails = new StudentDetails();
		EduService eduService = new EduService();
		EduInstituteService eduInstituteService = new EduInstituteService();
		EduStudentService eduStudentService = new EduStudentService();
		EduCounsellorService eduCounsellorService = new EduCounsellorService();
		Validations inputValidations = new Validations();
		EduDao eduDao = new EduDao();

		PrepareBean prepareBean = new PrepareBean();
		HttpSession session = null;
		int updatedRows = 0;
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			handleMultipartRequests(request, response);
			return;
		}
		String operationStr = getOperationNumber(request);
		int choice = 0;
		String choice2 = null;
		if (!SmValidator.isStringEmpty(operationStr)) {
			//choice = Integer.parseInt(operationStr);
			choice2 = operationStr;
		}
		System.out.println("Operation is: " + choice2);

		if (!isValidSession(request, choice2)) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		switch (choice2) {
		case CaseConstants.INSERT_USER_DETAILS:
			if(inputValidations.validationForInsertingUserDetails(request))
			{
				request.getRequestDispatcher("/signup.jsp").forward(request, response);
				return;
			}
			userDetails = prepareBean.beanForInsertUserDetails(request, userDetails);
			String emailFromUser = userDetails.getEmail();
			UserDetails userData = eduService.gettingUserDetailsByEmail(emailFromUser);
			if(userData.getUserId() != 0){
				request.setAttribute("errorMsg", "Existing Email Id");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
				
				
			}else{
				System.out.println("userDetails are:- " + userDetails);
				if (EmailEndpoint.sendVerificationEmail(userDetails)) {
					System.out.println("Successfully sent verification email to user: " + userDetails);
					request.setAttribute(Constants.SUCCESS_OP, "registration");
					updatedRows = eduService.insertingUserDetails(userDetails);
					System.out.println("Updated Rows are:- " + updatedRows);
					request.getRequestDispatcher("/successful-registration.jsp").forward(request, response);

				} else {

					request.getRequestDispatcher("/404.jsp").forward(request, response);
					return;

				}
			}

			/*
			System.out.println("userDetailsare:- " + userDetails);
			if (EmailEndpoint.sendVerificationEmail(userDetails)) {
				System.out.println("Successfully sent verification email to user: " + userDetails);
				request.setAttribute(Constants.SUCCESS_OP, "registration");
				updatedRows = eduService.insertingUserDetails(userDetails);
				System.out.println("Updated Rows are:- " + updatedRows);
				request.getRequestDispatcher("/successful-registration.jsp").forward(request, response);

			} else {

				request.getRequestDispatcher("/404.jsp").forward(request, response);
				return;

			}
*/			break;

		case CaseConstants.EMAIL_VERIFICATION:
			System.out.println("Verifying user via email");
			String token = request.getParameter(Constants.LBL_VERIFICATION_PARAMETER);
			System.out.println("Got verification token as: " + token);
			if (!SmValidator.isStringEmpty(token)) {
				try {
					if (EmailEndpoint.verifyPMUserEmail(token)) {
						request.setAttribute(Constants.SUCCESS_OP, "verification");
						request.getRequestDispatcher("/successful-registration.jsp").forward(request, response);
						return;
					}
				} catch (Exception e) {
					System.out.println("Error while sending verification email");
					e.printStackTrace();
					response.sendRedirect("/login.jsp");
				}
			} else {
				response.sendRedirect("/login.jsp");
			}

			break;

		case CaseConstants.LOGIN:
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			if(inputValidations.validationForLogin(request))
			{
				request.setAttribute("error", "Invalid username or password");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
			/*if (!InputValidator.isValidLoginCredentialInput(email, password)) {
				request.setAttribute("error", "Invalid username or password");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}*/
			userDetails = eduService.gettingUserDetailsByCredentials(email, password);
			int userId = userDetails.getUserId();
			if (userDetails != null && userDetails.getUserId() != 0) {
				if (userDetails.getEmail().equals(email) && userDetails.getPassword().equals(password)) {
					if (userDetails.getUserRole().equals(Constants.USER_ROLE_STUDENT)) {
						studentDetails = eduStudentService.getStudentDetailsByUserId(userId);
						System.out.println("studentdetails are:- " + studentDetails);

						if (!isProfileDetailsSubmitted(studentDetails)) {
							session = request.getSession(true);
							session.setAttribute(Constants.LBL_USERDETAILS, userDetails);
							request.getRequestDispatcher("/student-edu-form.jsp").forward(request, response);
							return;

						} else {
							session = request.getSession(true);
							session.setAttribute(Constants.LBL_USERDETAILS, userDetails);
							request.getRequestDispatcher("/Student_Index.jsp").forward(request, response);
							return;
						}
					} else if (userDetails.getUserRole().equals(Constants.USER_ROLE_INSTITUTE)) {
						
						InstituteRegistrationDetails instituteRegistrationDetails = eduInstituteService
								.getInstituteRegistrationDetailsByUserId(userId);
						
						if (!isCollegeRegistered(instituteRegistrationDetails)) {

							session = request.getSession(true);
							session.setAttribute(Constants.LBL_USERDETAILS, userDetails);
							List<CollegeDetails> allCollegeListWithDetails = eduInstituteService.gelAllCollegeDetails();
							System.out.println("allCollegeListWithDetails are:- " + allCollegeListWithDetails);
							List<CollegeDetails> collegeDetails = eduService.getCollegeDetails();
							List<StreamDetails> streamDetails = eduService.getStreamDetails();
							request.setAttribute("collegeDetails", collegeDetails);
							request.setAttribute("streamDetails", streamDetails);
							request.setAttribute("allCollegeListWithDetails", allCollegeListWithDetails);
							request.getRequestDispatcher("/institute_registration.jsp").forward(request, response);
						} else {
					
							session = request.getSession(true);
							session.setAttribute(Constants.LBL_USERDETAILS, userDetails);
							List<MonthlyEnquiryChartData> monthlyEnquiryChartDatas = new ArrayList<>();
							monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Jan", 20));
							monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Feb", 100));
							monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("March", 820));
							monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("April", 20));
							monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("May", 20));
							monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Jun", 20));
							monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Jul", 40));
							monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Aug", 55));
							monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Sept", 60));
							monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Oct", 70));
							monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Nov", 90));
							instituteRegistrationDetails = eduInstituteService.getInstituteRegistrationDetailsByUserId(userId);
							int collegeId = instituteRegistrationDetails.getCollegeId();
							userId = instituteRegistrationDetails.getUserId();
							userDetails = eduService.gettingUserDetailsById(userId);
							System.out.println("collegeId is:- " + collegeId);
							CollegeDetails collegeDetails = eduInstituteService.getSpecificDetailsByCollegeID(collegeId);
							System.out.println("instituteRegistrationDetails:- " + instituteRegistrationDetails);
							request.setAttribute("instituteRegistrationDetails", instituteRegistrationDetails);
							request.setAttribute("monthlyEnquiryChartDatas", monthlyEnquiryChartDatas);
							request.setAttribute("collegeDetails", collegeDetails);
							request.setAttribute("userDetails", userDetails);
							request.getRequestDispatcher("/Institute_Index.jsp").forward(request, response);
						}
					} else if (userDetails.getUserRole().equals(Constants.USER_ROLE_COUNSELLOR)) {
						session = request.getSession(true);
						session.setAttribute(Constants.LBL_USERDETAILS, userDetails);

						CounsellorProfileDetails counsellorProfileDetails = new CounsellorProfileDetails();
						userDetails = getUserFromSession(request);
						userId = userDetails.getUserId();
						counsellorProfileDetails = eduCounsellorService.getCounsellorProfileDetailsByUserId(userId);
						if (!isCounsellorProfileSubmitted(counsellorProfileDetails)) {
							request.getRequestDispatcher("/Counsellor-registration.jsp").forward(request, response);
							return;
						}
						int counsellor_profile_details_id = counsellorProfileDetails.getCounsellor_profile_details_id();
						/*
						  List<CounsellorEducationalBackground> allCounsellorEducationalBackground = eduCounsellorService.getCounsellorEduBgByCounsellorProfileId(counsellor_profile_details_id);
						  request.setAttribute("allCounsellorEducationalBackground", allCounsellorEducationalBackground);
						*/
						CounsellorEducationalBackground counselloredubg = eduCounsellorService.getSingleCounsellorEduBgByCounsellorProfileId(counsellor_profile_details_id);
						 request.setAttribute("counselloredubg", counselloredubg);
						 
						 CounsellorWorkExperience workExpDetail = eduCounsellorService.getSingleCounsellorWorkExp(counsellor_profile_details_id);
						 request.setAttribute("workExpDetail", workExpDetail);
						 
						 CounsellorPreferences eduprefdetail = eduCounsellorService.getSingleCounsellorEduPref(counsellor_profile_details_id);
						 request.setAttribute("eduprefdetail", eduprefdetail);
						 
						request.getRequestDispatcher("/Counselor-index.jsp").forward(request, response);
						
						// return;
					} else {
						System.out.println("userrole doesntmatch");
						 request.setAttribute("uRoleError", "User Role Doesn't Match");
						request.getRequestDispatcher("/login.jsp").forward(request, response);
					}
				} else {
					System.out.println("email and password doesnt match");
					request.setAttribute("loginError", "Email & Password Doesn't Match.");
				}
			} else {
				//request.setAttribute("error", "Invalid username or password");
				request.setAttribute("loginError", "Invalid Email Or Password.");
				System.out.println("uDetailsForLogin are null");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
			break;

		case CaseConstants.USER_PROFILE:

			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			//TODO: remove the following code
			if (userDetails == null) {
				response.sendRedirect("/login.jsp");
			} else {
				/* USER PROFILE DATA START */
				userDetails = EduService.getProfileDataOfUser(userId);
				studentDetails = eduStudentService.getStudentDetailsByUserId(userId);
				System.out.println("Getting student details by userId:-" + studentDetails);
				request.setAttribute("userDetails", userDetails);
				request.setAttribute("studentDetails", studentDetails);
				/* USER PROFILE DATA END */

				String dashboardPage = request.getParameter("dashboardPage");
				StudentDetails studentdetails = eduStudentService.getStudentDetailsByUserId(userId);
				int studentId = studentdetails.getStudent_id();

				PersonalInfoDetails personalInfoDetail = eduStudentService.getPersonalInfoDetailsById(studentId);
				System.out.println("personalInfoDetail are:- " + personalInfoDetail);
				request.setAttribute("personalInfoDetail", personalInfoDetail);

				EduBgDetails edubgdetail = eduService.getSingleEduBgDetailsById(studentId);
				System.out.println("edu bg detail is:- " + edubgdetail);
				request.setAttribute("edubgdetail", edubgdetail);

				WorkExperienceDetails experienceDetail = eduService.getSingleWorkExpDetailsById(studentId);
				System.out.println("Work Experience detail are:- " + experienceDetail);
				request.setAttribute("experienceDetail", experienceDetail);

				EduPrefDetails eduprefdetail = eduDao.getSingleEduPrefDetailsById(studentId);
				System.out.println("Edu Preference detail are:- " + experienceDetail);
				request.setAttribute("eduprefdetail", eduprefdetail);

				if (!SmValidator.isStringEmpty(dashboardPage)) {

					if (dashboardPage.equals(PageConstants.MY_PROFILE)) {

						request.getRequestDispatcher("/student-profile.jsp").forward(request, response);
						return;
					} else if (dashboardPage.equals(PageConstants.EDIT_PROFILE)) {
						request.getRequestDispatcher("/edit-profile.jsp").forward(request, response);
						return;
					} else if (dashboardPage.equals(PageConstants.ADD_EDU_BG_DETAILS)) {
						request.getRequestDispatcher("/add-edu-bg.jsp").forward(request, response);
						return;
					} else if (dashboardPage.equals(PageConstants.ADD_WORK_EXP_DETAILS)) {
						request.getRequestDispatcher("/add-work-exp.jsp").forward(request, response);
						return;
					} else if (dashboardPage.equals(PageConstants.ADD_EDU_PREF_DETAILS)) {
						request.getRequestDispatcher("/edu-pref.jsp").forward(request, response);
						return;
					} else if (dashboardPage.equals(PageConstants.ADD_PERSONAL_INFO)) {
						request.getRequestDispatcher("/add-personalinfo.jsp").forward(request, response);
						return;
					} else if (dashboardPage.equals(PageConstants.EDIT_PERSONAL_INFO)) {
						request.getRequestDispatcher("/edit-personalinfo.jsp").forward(request, response);
						return;
					} else if (dashboardPage.equals(PageConstants.QA_FORUM)) {
						request.getRequestDispatcher("/question_answer_forum.jsp").forward(request, response);
						return;
					} else {
						request.getRequestDispatcher("/student-profile.jsp").forward(request, response);
						return;
					}

				}

				request.getRequestDispatcher("/student-profile.jsp").forward(request, response);
			}
			break;

		case CaseConstants.RENDER_STUDENT_PROFILE_PIC:
			System.out.println("case to render student profile pic");
			String userId2 = request.getParameter("userId");
			String imageName = request.getParameter("imageName");

			if (SmValidator.isStringEmpty(userId2) || SmValidator.isStringEmpty(imageName))
				return;

			if (!userId2.matches(Constants.REGEX_FOR_DIGIT))
				return;

			String imagePath = Constants.APP_DATA_LOCATION + File.separator + Constants.USER_ROLE_1 + File.separator
					+ userId2 + File.separator + Constants.IMAGES_TYPE_LOCATION + File.separator + imageName;

			// MiscUtils.renderImage(response, imagePath);
			MiscUtils.renderImage(response, imagePath);

			break;

		case CaseConstants.RENDER_INSTITUTE_PROFILE_PIC:
			System.out.println("case to render institue profile pic");

			int instituteId = Integer.parseInt(request.getParameter("instituteId"));
			imageName = request.getParameter("imageName");
			System.out.println("instituteId:- " + instituteId + "\n" + "imageName" + imageName);

			imagePath = Constants.APP_DATA_LOCATION + File.separator + Constants.USER_ROLE_2 + File.separator
					+ instituteId + File.separator + Constants.IMAGES_TYPE_LOCATION + File.separator + imageName;

			// MiscUtils.renderImage(response, imagePath);
			MiscUtils.renderImage(response, imagePath);

			break;
			
		case CaseConstants.RENDER_COLLEGE_PROFILE_PIC:
			/*System.out.println("case to render institue profile pic");

			int instituteId = Integer.parseInt(request.getParameter("instituteId"));
			imageName = request.getParameter("imageName");
			System.out.println("instituteId:- " + instituteId + "\n" + "imageName" + imageName);

			imagePath = Constants.APP_DATA_LOCATION + File.separator + Constants.USER_ROLE_2 + File.separator
					+ instituteId + File.separator + Constants.IMAGES_TYPE_LOCATION + File.separator + imageName;

			// MiscUtils.renderImage(response, imagePath);
			MiscUtils.renderImage(response, imagePath);*/
			
			int collegeId = Integer.parseInt(request.getParameter("collegeId"));
			imageName = request.getParameter("imageName");
			System.out.println("collegeId:- " + collegeId + "\n" + "imageName" + imageName);

			imagePath = Constants.APP_DATA_LOCATION + File.separator + Constants.COLLEGE + File.separator
					+ collegeId + File.separator + Constants.IMAGES_TYPE_LOCATION + File.separator + imageName;

			// MiscUtils.renderImage(response, imagePath);
			MiscUtils.renderImage(response, imagePath);
			
			break;
		case CaseConstants.RENDER_COLLEGE_OTHER_IMAGES:
			/*System.out.println("case to render institue profile pic");

			int instituteId = Integer.parseInt(request.getParameter("instituteId"));
			imageName = request.getParameter("imageName");
			System.out.println("instituteId:- " + instituteId + "\n" + "imageName" + imageName);

			imagePath = Constants.APP_DATA_LOCATION + File.separator + Constants.USER_ROLE_2 + File.separator
					+ instituteId + File.separator + Constants.IMAGES_TYPE_LOCATION + File.separator + imageName;

			// MiscUtils.renderImage(response, imagePath);
			MiscUtils.renderImage(response, imagePath);*/
			
			collegeId = Integer.parseInt(request.getParameter("collegeId"));
			imageName = request.getParameter("imageName");
			System.out.println("collegeId:- " + collegeId + "\n" + "imageName" + imageName);

			imagePath = Constants.APP_DATA_LOCATION + File.separator + Constants.COLLEGE + File.separator
					+ collegeId + File.separator + Constants.COLLEGE_OTHER_IMAGES + File.separator + imageName;

			// MiscUtils.renderImage(response, imagePath);
			MiscUtils.renderImage(response, imagePath);
			
			break;

		case CaseConstants.RENDER_INSTITUTE_IMAGES:
			System.out.println("case to RENDER_INSTITUTE_IMAGES");
			instituteId = Integer.parseInt(request.getParameter("instituteId"));
			imageName = request.getParameter("imageName");

			if (SmValidator.isIntEmpty(instituteId) || SmValidator.isStringEmpty(imageName))
				return;

			/*
			 * if(!instituteId.matches(Constants.REGEX_FOR_DIGIT)) return;
			 */
			/*
			 * imagePath = Constants.APP_DATA_LOCATION + File.separator +
			 * Constants.USER_ROLE_1 + File.separator + userId2+ File.separator
			 * + Constants.IMAGES_TYPE_LOCATION + File.separator + imageName;
			 */

			// MiscUtils.renderImage(response, imagePath);
			// MiscUtils.renderImage(response, imagePath);

			break;

		case CaseConstants.GET_SINGLE_INSTITUTE_DETAILS:
			System.out.println("THIS IS GET_SINGLE_INSTITUTE_DETAILS");
			collegeId = Integer.parseInt(request.getParameter("collegeId"));
		
			if(inputValidations.validationForGetSingleInstituteDetails(request))
			{
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			
			System.out.println("college is:- " + collegeId);
			/*RegisteredInstituteDetails registeredInstituteDetails = eduInstituteService.getRegisteredInstituteDetailsByInstituteId(instituteId);
			
			InstituteRegistrationDetails instituteRegistrationDetails = eduInstituteService.getInstituteRegistrationDetailsByInstituteId(instituteId);
		//	collegeId = instituteRegistrationDetails.getCollegeId();
*/			
			CollegeDetails collegeDetails = eduInstituteService.getSpecificDetailsByCollegeID(collegeId);
			List<StreamDetails> getStreamdetails = eduInstituteService.getStreamDetailsByInstituteID(collegeId);
			List<CourseDetails> getCoursedetails = eduInstituteService.getCourseDetailsByInstituteID(collegeId);
			
			List<QAForumDetails> messages = eduService.getAllMessage();
			System.out.println("messages are:- " + messages);
			System.out.println("collegeDetails:- "+ collegeDetails);
			request.setAttribute("messages", messages);
			request.setAttribute("getStreamdetails", getStreamdetails);
			request.setAttribute("getCoursedetails", getCoursedetails);
			/*request.setAttribute("instituteRegistrationDetails", instituteRegistrationDetails);
			request.setAttribute("registeredInstituteDetails", registeredInstituteDetails);*/
			request.setAttribute("collegeDetails", collegeDetails);
			request.getRequestDispatcher("/edu-institute-details.jsp").forward(request, response);

			break;

		case CaseConstants.INSERT_INSTITUTE_LEADS:
			
			/*if(inputValidations.validationForInsertInstituteDetails(request))
			{
				request.getRequestDispatcher("/edu-institute-details.jsp").forward(request, response);
				return;
			}*/
			
			collegeId = Integer.parseInt(request.getParameter("collegeId"));
			InstituteLeads instituteLeads = prepareBean.beanForInsertLeads(request);
			
			updatedRows = eduInstituteService.insertInstituteLeads(instituteLeads);
			request.getRequestDispatcher("/index.jsp").forward(request, response);

			/*List<EduKeyValue> keyValuesForInsReg = new ArrayList<>();
			keyValuesForInsReg
					.add(new EduKeyValue(Constants.LBL_OPERATION, String.valueOf(CaseConstants.GET_SINGLE_INSTITUTE_DETAILS)));
			keyValuesForInsReg.add(new EduKeyValue("instituteId", String.valueOf(
					(SmValidator.isIntEmpty(instituteLeads.getInstituteId()) ? 0 : instituteLeads.getInstituteId()))));
			keyValuesForInsReg.add(new EduKeyValue("message", "Single institute details retrieved."));
			response.sendRedirect(
					request.getContextPath() + Constants.LBL_CONTROLLER + EduUtils.prepareURLKeyValues(keyValuesForInsReg));*/
			break;


		case CaseConstants.INSERT_PERSONAL_INFO_DETAILS:
			int updatedRows4 = 0;
			if(inputValidations.validationForInsertPersonalInfo(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			PersonalInfoDetails personalInfodetails = prepareBean.beanForInsertStudentPersonalInfoDetails(request);
			System.out.println("personal details are: " + personalInfodetails);
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			userDetails = EduService.getProfileDataOfUser(userDetails.getUserId());
			EduStudentDao eduStudentDao = new EduStudentDao();
			studentDetails = eduStudentDao.getStudentDetailsByUserId(userId);
			int studentId = studentDetails.getStudent_id();
			personalInfodetails.setStudentId(studentId);
			updatedRows4 = eduStudentDao.insertPersonalInofDetail(personalInfodetails);

			System.out.println("updated rows are :- " + updatedRows4);
			/*
			 * int personalInfoId = personalInfodetails.getPersonalInfoId();
			 * System.out.println("personalInfoId is:- " + personalInfoId);
			 */
			List<EduKeyValue> keyValues = new ArrayList<>();
			keyValues.add(new EduKeyValue(Constants.LBL_OPERATION, String.valueOf(CaseConstants.USER_PROFILE)));
			keyValues.add(
					new EduKeyValue("personalInfoId", String.valueOf((SmValidator.isIntEmpty(userId) ? 0 : userId))));
			keyValues.add(new EduKeyValue("message", "personalInfo details are updated."));
			response.sendRedirect(request.getContextPath() + Constants.LBL_CONTROLLER + EduUtils.prepareURLKeyValues(keyValues));
			/*
			 * request.getRequestDispatcher("/personalinfo.jsp").forward(
			 * request, response);
			 */
			break;

		case CaseConstants.GET_PERSONAL_INFO_DETAILS:
			int personalInfoId = Integer.parseInt(request.getParameter("personalInfoId"));
			if(inputValidations.validationForGetPersonalInfo(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			studentDetails = new StudentDetails();
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();

			studentDetails = eduStudentService.getStudentDetailsByUserId(userId);
			studentId = studentDetails.getStudent_id();
			personalInfodetails = eduStudentService.getPersonalInfoDetailsById(studentId);
			System.out.println("Details to be inserted are:- " + personalInfodetails);
			request.setAttribute("personalInfodetails", personalInfodetails);

			/* USER PROFILE DATA START */
			userDetails = EduService.getProfileDataOfUser(userId);

			studentDetails = eduStudentService.getStudentDetailsByUserId(userId);
			System.out.println("Getting student details by userId:-" + studentDetails);
			request.setAttribute("userDetails", userDetails);
			request.setAttribute("studentDetails", studentDetails);
			/* USER PROFILE DATA END */

			request.getRequestDispatcher("/edit-personalinfo.jsp").forward(request, response);

			/*
			 * List<EduKeyValue> keyValuesForpersonalInfoId = new ArrayList<>();
			 * keyValuesForpersonalInfoId.add(new EduKeyValue(Constants.LBL_OPERATION,
			 * String.valueOf(CaseConstants.USER_PROFILE)));
			 * keyValuesForpersonalInfoId.add(new EduKeyValue("personalInfoId",
			 * String.valueOf((SmValidator.isIntEmpty(personalInfoId) ? 0 :
			 * personalInfoId)))); keyValuesForpersonalInfoId.add(new
			 * EduKeyValue("message", "personalInfo details are updated."));
			 */

			break;

		/*
		 * case CaseConstants.EDIT_PERSONAL_INFO_DETAILS:
		 * 
		 * break;
		 */
		case CaseConstants.UPDATE_PERSONAL_INFO_DETAILS:
			personalInfoId = Integer.parseInt(request.getParameter("personalInfoId"));
			if(inputValidations.validationForUpdatePersonalInfo(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			PersonalInfoDetails personalInfoDetail = prepareBean.beanForUpdatePersonalInfoDetails(request);
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			userDetails = EduService.getProfileDataOfUser(userDetails.getUserId());
			studentDetails = eduStudentService.getStudentDetailsByUserId(userId);
			studentId = studentDetails.getStudent_id();
			updatedRows = eduStudentService.UpdatePersonalInfoDetailsById(personalInfoId, personalInfoDetail);

			keyValues = new ArrayList<>();
			keyValues.add(new EduKeyValue(Constants.LBL_OPERATION, String.valueOf(CaseConstants.USER_PROFILE)));
			keyValues.add(new EduKeyValue("personalInfoId",
					String.valueOf((SmValidator.isIntEmpty(personalInfoId) ? 0 : personalInfoId))));
			keyValues.add(new EduKeyValue("message", "personalInfo details are updated."));
			response.sendRedirect(request.getContextPath() + Constants.LBL_CONTROLLER + EduUtils.prepareURLKeyValues(keyValues));

			break;
		case CaseConstants.INSERT_INSTITUTE_STREAMS_COURSES:
			System.out.println("this is INSERT_INSTITUTE_STREAMS_COURSES case");

			int streamCourseIndex = Integer.parseInt(request.getParameter("streamCourseIndex"));
			System.out.println("streamCourseIndex" + streamCourseIndex);
			if(inputValidations.validationForInsertInstituteStreamsCourses(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			instituteId = Integer.parseInt(request.getParameter("instituteId"));
			System.out.println("instituteId" + instituteId);

			for (int i = 0; i <= streamCourseIndex; i++) {
				CollegeCourseMapping collegeCourseMapping = new CollegeCourseMapping();

				int courseIdInTable = Integer.parseInt(request.getParameter("courseIdInTable" + i));

				System.out.println("courseIdInTable" + courseIdInTable);

				collegeCourseMapping.setCollegeId(instituteId);
				collegeCourseMapping.setCourseId(Integer.parseInt(request.getParameter("courseIdInTable" + i)));

				System.out.println("collegeCourseMapping college id:- " + collegeCourseMapping.getCollegeId()
						+ "course id:-" + collegeCourseMapping.getCourseId());

				updatedRows = eduInstituteService.insertCollegeCourseMappingDetails(collegeCourseMapping);
				System.out.println("collegeCourseMapping:- " + updatedRows);

			}
			List<EduKeyValue> keyValuesForInsReg = new ArrayList<>();
			keyValuesForInsReg.add(new EduKeyValue(Constants.LBL_OPERATION, String.valueOf(CaseConstants.MONTHLY_ENQUIRIES)));
			keyValuesForInsReg.add(
					new EduKeyValue("userId", String.valueOf((SmValidator.isIntEmpty(instituteId) ? 0 : instituteId))));
			keyValuesForInsReg.add(new EduKeyValue("message", "Monthly Enquires are updated."));
			response.sendRedirect(
					request.getContextPath() + Constants.LBL_CONTROLLER + EduUtils.prepareURLKeyValues(keyValuesForInsReg));

			break;

		case CaseConstants.INSERT_EDU_BG_DETAILS:
			if(inputValidations.validationForInsertEduBgDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			List<EduBgDetails> edubgdetails = prepareBean.beanForInsertStudentEduBgDetails(request);
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			updatedRows = 0;
			UserDetails userDetails2 = EduService.getProfileDataOfUser(userDetails.getUserId());
			studentDetails = eduStudentService.getStudentDetailsByUserId(userDetails2.getUserId());
			System.out.println("Getting student details by userId:-" + studentDetails);
			request.setAttribute("userDetails2", userDetails2);
			request.setAttribute("studentDetails", studentDetails);

			StudentDetails studentdetails = eduStudentService.getStudentDetailsByUserId(userId);
			/*
			 * StudentDetails studentdetails =
			 * eduDao.getStudentDetailsByUserId(userId);
			 */
			for (EduBgDetails edubgdetail : edubgdetails) {
				edubgdetail.setStudentId(studentdetails.getStudent_id());
				updatedRows = eduStudentService.insertEduBgDetails(edubgdetail);
				/* updatedRows = eduDao.insertEduBgDetails(edubgdetail); */
			}
			System.out.println("updated rows are:- " + updatedRows);
			request.getRequestDispatcher("/add-edu-bg.jsp").forward(request, response);
			break;

		case CaseConstants.GET_EDU_BG_DETAILS:
			studentDetails = new StudentDetails();
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			studentdetails = eduStudentService.getStudentDetailsByUserId(userId);
			/* studentdetails = eduDao.getStudentDetailsByUserId(userId); */
			studentId = studentdetails.getStudent_id();
			List<EduBgDetails> allEdubgdetails = eduStudentService.getEduBgDetailsByEduBGId(studentId);

			/*
			 * List<EduBgDetails> allEdubgdetails =
			 * eduDao.getEduBgDetailsById(studentId);
			 */
			System.out.println("edubgdetail to be insertedis:- " + allEdubgdetails);

			userDetails = EduService.getProfileDataOfUser(userId);
			studentDetails = eduStudentService.getStudentDetailsByUserId(userId);
			System.out.println("Getting student details by userId:-" + studentDetails);
			request.setAttribute("userDetails", userDetails);
			request.setAttribute("studentDetails", studentDetails);

			request.setAttribute("allEdubgdetails", allEdubgdetails);
			/*
			 * for(EduBgDetails edubgdetail: allEdubgdetails) {
			 * System.out.println("edubgdetail to be insertedis:- " +
			 * edubgdetail); request.setAttribute("edubgdetail", edubgdetail); }
			 */

			request.getRequestDispatcher("/showAllEduBGDetails.jsp").forward(request, response);

			/*
			 * edubgdetails = new EduBgDetails(); session =
			 * getUserFromSession(request); uid = session.getUserId();
			 * studentdetails = eduDao.getStudentDetailsByUserId(uid);
			 * edubgdetails =
			 * eduDao.getEduBgDetailsById(studentdetails.getStudent_id());
			 * request.setAttribute("edubgdetails", edubgdetails);
			 * System.out.println("edubgdetails are:- " + edubgdetails);
			 * request.getRequestDispatcher("/profile.jsp").forward(request,
			 * response);
			 */
			break;

		case CaseConstants.EDIT_EDU_BG_DETAILS:
			int eduBgId = Integer.parseInt(request.getParameter("eduBgId"));
			if(inputValidations.validationForEditEduBgDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			System.out.println("eduBgId is:- " + eduBgId);
			EduBgDetails eduBgDetail = eduStudentService.getEduBgDetailsByEduBgId(eduBgId);
			/*
			 * EduBgDetails eduBgDetail =
			 * eduDao.getEduBgDetailsByEduBgId(eduBgId);
			 */
			System.out.println("edubgdetails are:- " + eduBgDetail);

			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			userDetails = EduService.getProfileDataOfUser(userId);
			studentDetails = eduStudentService.getStudentDetailsByUserId(userId);
			System.out.println("Getting student details by userId:-" + studentDetails);
			request.setAttribute("userDetails", userDetails);
			request.setAttribute("studentDetails", studentDetails);

			request.setAttribute("eduBgDetail", eduBgDetail);
			request.getRequestDispatcher("/edit-edu-bg.jsp").forward(request, response);
			break;

		case CaseConstants.UPDATE_EDU_BG_DETAILS:
			eduBgId = Integer.parseInt(request.getParameter("eduBgId"));
			if(inputValidations.validationForUpdateEduBgDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			EduBgDetails eduBgDetailBean = prepareBean.beanForUpdateSingleEduBgDetailsByEduBgId(eduBgId, request);
			eduBgDetailBean.setEdubgId(eduBgId);
			updatedRows = eduStudentService.updateEduBgDetailsByEduBgID(eduBgId, eduBgDetailBean);
			System.out.println("Edu BG details updated Rows are:- " + updatedRows);

			keyValues = new ArrayList<>();
			keyValues.add(new EduKeyValue(Constants.LBL_OPERATION, String.valueOf(CaseConstants.USER_PROFILE)));
			keyValues.add(new EduKeyValue("eduBgId", String.valueOf((SmValidator.isIntEmpty(eduBgId) ? 0 : eduBgId))));
			keyValues.add(new EduKeyValue("message", "Edu BG details are updated."));
			// forward to PRODUCTS case
			response.sendRedirect(request.getContextPath() + Constants.LBL_CONTROLLER + EduUtils.prepareURLKeyValues(keyValues));

			break;

		case CaseConstants.INSERT_WORK_EXP:
			if(inputValidations.validationForInsertWorkExpDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			List<WorkExperienceDetails> workexpdetails = prepareBean.beanForInsertStudentWorkExpDetails(request);
			System.out.println("Work exp details are: - " + workexpdetails);
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			updatedRows = 0;
			userDetails2 = EduService.getProfileDataOfUser(userDetails.getUserId());
			studentDetails = eduStudentService.getStudentDetailsByUserId(userDetails2.getUserId());
			System.out.println("Getting student details by userId:-" + studentDetails);
			request.setAttribute("userDetails2", userDetails2);
			request.setAttribute("studentDetails", studentDetails);

			studentdetails = eduStudentService.getStudentDetailsByUserId(userId);
			for (WorkExperienceDetails workexpdetail : workexpdetails) {
				workexpdetail.setStudentId(studentdetails.getStudent_id());
				updatedRows = eduStudentService.insertWorkExpDetails(workexpdetail);
			}
			System.out.println("updated rows are:- " + updatedRows);
			request.getRequestDispatcher("/add-work-exp.jsp").forward(request, response);
			break;

		case CaseConstants.GET_WORK_EXP:

			studentDetails = new StudentDetails();
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			studentdetails = eduStudentService.getStudentDetailsByUserId(userId);
			/* studentdetails = eduDao.getStudentDetailsByUserId(userId); */
			studentId = studentdetails.getStudent_id();
			List<WorkExperienceDetails> allWorkExpdetails = eduStudentService.getWorkExpDetailsByStudentId(studentId);

			/*
			 * List<EduBgDetails> allEdubgdetails =
			 * eduDao.getEduBgDetailsById(studentId);
			 */
			System.out.println("work exp to be insertedis:- " + allWorkExpdetails);

			userDetails = EduService.getProfileDataOfUser(userId);
			studentDetails = eduStudentService.getStudentDetailsByUserId(userId);
			System.out.println("Getting student details by userId:-" + studentDetails);
			request.setAttribute("userDetails", userDetails);
			request.setAttribute("studentDetails", studentDetails);

			request.setAttribute("allWorkExpdetails", allWorkExpdetails);
			/*
			 * for(EduBgDetails edubgdetail: allEdubgdetails) {
			 * System.out.println("edubgdetail to be insertedis:- " +
			 * edubgdetail); request.setAttribute("edubgdetail", edubgdetail); }
			 */

			request.getRequestDispatcher("/showAllWorkExpDetails.jsp").forward(request, response);

			/*
			 * workexpdetails = new WorkExpDetails(); session =
			 * getUserFromSession(request); int uid1 = session.getUserId();
			 * studentdetails = eduDao.getStudentDetailsByUserId(uid1);
			 * workexpdetails =
			 * eduDao.getWorkExpById(studentdetails.getStudent_id());
			 * System.out.println("workexpdetails are :- " + workexpdetails);
			 * request.setAttribute("workexpdetails", workexpdetails);
			 * request.getRequestDispatcher("/profile.jsp").forward(request,
			 * response);
			 */
			break;

		case CaseConstants.EDIT_EDU_WORK_EXP_DETAILS:

			int eduWorkExpId = Integer.parseInt(request.getParameter("eduWorkExpId"));
			if(inputValidations.validationForEditEduWorkExpDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			System.out.println("eduWorkExpId is:- " + eduWorkExpId);
			WorkExperienceDetails workExperienceDetail = eduStudentService
					.getEduWorkExpDetailsByeduWorkExpId(eduWorkExpId);
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			userDetails = EduService.getProfileDataOfUser(userId);
			studentDetails = eduStudentService.getStudentDetailsByUserId(userId);
			System.out.println("Getting student details by userId:-" + studentDetails);
			request.setAttribute("userDetails", userDetails);
			request.setAttribute("studentDetails", studentDetails);

			request.setAttribute("workExperienceDetail", workExperienceDetail);
			request.getRequestDispatcher("/edit-work-exp.jsp").forward(request, response);
			/*
			 * ; EduBgDetails eduBgDetail =
			 * eduDao.getEduBgDetailsByEduBgId(eduBgId); System.out.println(
			 * "edubgdetails are:- " + eduBgDetail);
			 * 
			 * userDetails = getUserFromSession(request); userId =
			 * userDetails.getUserId(); userDetails =
			 * EduService.getProfileDataOfUser(userId); studentDetails =
			 * eduService.getStudentDetailsByUserId(userId); System.out.println(
			 * "Getting student details by userId:-" + studentDetails);
			 * request.setAttribute("userDetails", userDetails);
			 * request.setAttribute("studentDetails", studentDetails);
			 * 
			 * request.setAttribute("eduBgDetail", eduBgDetail);
			 * request.getRequestDispatcher("/edit-edu-bg.jsp").forward(request,
			 * response);
			 */
			break;

		case CaseConstants.UPDATE_WORK_EXP:
			int workExpId = Integer.parseInt(request.getParameter("workExpId"));
			if(inputValidations.validationForUpdateWorkExpDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			WorkExperienceDetails experienceDetails = prepareBean
					.beanForUpdateSingleWorkExpDetailsByEorkExpId(workExpId, request);
			experienceDetails.setWorkExpId(workExpId);
			updatedRows = eduStudentService.updateWorkExpDetailsByWorkExpID(workExpId, experienceDetails);
			System.out.println("Work Exp details updated Rows are:- " + updatedRows);

			List<EduKeyValue> keyValuesForWorkExp = new ArrayList<>();
			keyValuesForWorkExp.add(new EduKeyValue(Constants.LBL_OPERATION, String.valueOf(CaseConstants.USER_PROFILE)));
			keyValuesForWorkExp.add(
					new EduKeyValue("eduBgId", String.valueOf((SmValidator.isIntEmpty(workExpId) ? 0 : workExpId))));
			keyValuesForWorkExp.add(new EduKeyValue("message", "Edu BG details are updated."));
			// forward to PRODUCTS case
			response.sendRedirect(
					request.getContextPath() + Constants.LBL_CONTROLLER + EduUtils.prepareURLKeyValues(keyValuesForWorkExp));

			//
			/*
			 * workexpdetails = new WorkExpDetails(); workexpdetails =
			 * prepareBean.beanForUpdateStudentWorkExpDetails(request,
			 * workexpdetails); session = getUserFromSession(request); uid1 =
			 * session.getUserId(); studentdetails =
			 * eduDao.getStudentDetailsByUserId(uid1); workexpdetails =
			 * eduDao.updateWorkExpById(studentdetails.getStudent_id());
			 */
			break;

		case CaseConstants.INSERT_EDU_PREF:
			int updatedRows3 = 0;
			if(inputValidations.validationForInsertEduPrefDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			List<EduPrefDetails> eduprefdetails = prepareBean.beanForInsertEduPreference(request);
			System.out.println("edupref details are : " + eduprefdetails);
			/*
			 * session = getUserFromSession(request); uid = session.getUserId();
			 */
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();

			for (EduPrefDetails eduprefdetail : eduprefdetails) {
				studentdetails = eduStudentService.getStudentDetailsByUserId(userId);
				int stu_id = studentdetails.getStudent_id();
				eduprefdetail.setStudentId(stu_id);
				updatedRows3 = eduStudentService.insertEducationPreference(eduprefdetail);
			}
			System.out.println("updated rows are:- " + updatedRows3);
			request.getRequestDispatcher("/add-edu-pref.jsp").forward(request, response);
			break;

		case CaseConstants.GET_EDU_PREF_DETAILS:
			studentDetails = new StudentDetails();
			/*
			 * session = getUserFromSession(request); uid = session.getUserId();
			 */
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			studentdetails = eduStudentService.getStudentDetailsByUserId(userId);
			studentId = studentdetails.getStudent_id();

			List<EduPrefDetails> allEduPrefDetails = eduStudentService.getEduPrefDetailsById(studentId);
			System.out.println("eduPrefDetails inserted are : " + allEduPrefDetails);
			request.setAttribute("allEduPrefDetails", allEduPrefDetails);

			request.getRequestDispatcher("/showAllEduPrefDetails.jsp").forward(request, response);

			break;

		case CaseConstants.EDIT_EDU_PREF_DETAILS:
			int eduPrefId = Integer.parseInt(request.getParameter("eduPrefId"));
			if(inputValidations.validationForEditEduPrefDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			System.out.println("eduPrefId is : " + eduPrefId);

			EduPrefDetails eduPrefDetail = eduStudentService.getEduPrefDetailsByEduPrefId(eduPrefId);
			System.out.println("eduPrefDetail are:- " + eduPrefDetail);
			request.setAttribute("eduPrefDetail", eduPrefDetail);
			request.getRequestDispatcher("/edit-edu-pref.jsp").forward(request, response);
			break;

		case CaseConstants.UPDATE_EDU_PREF_DETAILS:
			eduPrefId = Integer.parseInt(request.getParameter("eduPrefId"));
			if(inputValidations.validationForUpdateEduPrefDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			EduPrefDetails eduPrefDetailBean = prepareBean.beanForUpdateSingleEduPrefDetailsByEduPrefId(eduPrefId,
					request);
			eduPrefDetailBean.setEduPrefId(eduPrefId);
			updatedRows = eduStudentService.updateEduPrefDetailsByEduPrefID(eduPrefId, eduPrefDetailBean);
			System.out.println("updates rows are: - " + updatedRows);
			List<EduKeyValue> keyValuesForEduPref = new ArrayList<>();
			keyValuesForEduPref.add(new EduKeyValue(Constants.LBL_OPERATION, String.valueOf(CaseConstants.USER_PROFILE)));
			keyValuesForEduPref.add(
					new EduKeyValue("eduPrefId", String.valueOf((SmValidator.isIntEmpty(eduPrefId) ? 0 : eduPrefId))));
			keyValuesForEduPref.add(new EduKeyValue("message", "Edu Pref details are updated."));
			response.sendRedirect(
					request.getContextPath() + Constants.LBL_CONTROLLER + EduUtils.prepareURLKeyValues(keyValuesForEduPref));
			break;
		case CaseConstants.INSERT_INSTITUTE_DETAILS:
			System.out.println("insert institute details");
			if(inputValidations.validationForInsertInstituteDetails(request))
			{
				request.getRequestDispatcher("/institute_registration.jsp").forward(request, response);
				return;
			}
			collegeId = Integer.parseInt(request.getParameter("collegeId"));
			collegeDetails = eduInstituteService.getSpecificDetailsByCollegeID(collegeId);
			
			InstituteRegistrationDetails instituteRegistrationDetails = prepareBean.beanForInsertInstituteRegistrationDetails(request);
			instituteRegistrationDetails.setInstituteName(collegeDetails.getCollegeName());
			
			/*userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			instituteRegistrationDetails.setUserId(userId);
			*/
			/*session = request.getSession(true);
			session.setAttribute(Constants.LBL_USERDETAILS, userDetails);*/
			/*userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();*/
			userId = Integer.parseInt(request.getParameter("userId"));
			System.out.println("User id is:- " + userId);
			instituteRegistrationDetails.setUserId(userId);
			updatedRows = eduInstituteService.insertInstitureRegistrationDetails(instituteRegistrationDetails);
			System.out.println("register institute updated rows are:" + updatedRows);
			//instituteRegistrationDetails = eduInstituteService.getInstituteRegistrationDetailsByUserId(userId);
			
			
			
			List<MonthlyEnquiryChartData> monthlyEnquiryChartDatas = new ArrayList<>();
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Jan", 20));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Feb", 100));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("March", 820));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("April", 20));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("May", 20));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Jun", 20));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Jul", 40));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Aug", 55));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Sept", 60));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Oct", 70));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Nov", 90));
			
			instituteRegistrationDetails = eduInstituteService.getInstituteRegistrationDetailsByUserId(userId);
			collegeId = instituteRegistrationDetails.getCollegeId();
			System.out.println("collegeId is:- " + collegeId);
			collegeDetails = eduInstituteService.getSpecificDetailsByCollegeID(collegeId);
			System.out.println("instituteRegistrationDetails:- " + instituteRegistrationDetails);
			request.setAttribute("instituteRegistrationDetails", instituteRegistrationDetails);
			request.setAttribute("monthlyEnquiryChartDatas", monthlyEnquiryChartDatas);
			request.setAttribute("collegeDetails", collegeDetails);
			request.setAttribute("instituteRegistrationDetails", instituteRegistrationDetails);
			request.getRequestDispatcher("/Institute_Index.jsp").forward(request, response);
			
			/*keyValuesForEduPref = new ArrayList<>();
			keyValuesForEduPref.add(new EduKeyValue(Constants.LBL_OPERATION, String.valueOf(CaseConstants.MONTHLY_ENQUIRIES)));
			keyValuesForEduPref.add(
					new EduKeyValue("userId", String.valueOf((SmValidator.isIntEmpty(userId) ? 0 : userId))));
			keyValuesForEduPref.add(new EduKeyValue("message", "Monthly enquiries details are updated."));
			response.sendRedirect(
					request.getContextPath() + Constants.LBL_CONTROLLER + EduUtils.prepareURLKeyValues(keyValuesForEduPref));*/
			//* insert college-stream mapping */
			/*
			 * CollegeStreamMapping collegeStreamMapping = new
			 * CollegeStreamMapping(); String streamDetails[] =
			 * request.getParameterValues("streamDetails"); for (String
			 * streamDetail : streamDetails) { int streamId =
			 * Integer.parseInt(streamDetail);
			 * 
			 * collegeStreamMapping.setCollegeId(instituteRegistrationDetails.
			 * getInstitueRegID()); collegeStreamMapping.setStreamId(streamId);
			 * System.out.println("College ID: " +
			 * instituteRegistrationDetails.getInstitueRegID() + "\n" +
			 * "Stream ID: " + streamId); updatedRows =
			 * eduService.insertCollegeStreamMappingDetails(collegeStreamMapping
			 * ); } System.out.println(
			 * "register college-stream updated rows are:" + updatedRows);
			 */
			/* insert college-Course mapping */

			/*
			 * 
			 * CollegeCourseMapping collegeCourseMapping = new
			 * CollegeCourseMapping(); String courseDetails[] =
			 * request.getParameterValues("courseDetails"); for (String
			 * courseDetail : courseDetails) { int courseId =
			 * Integer.parseInt(courseDetail);
			 * collegeCourseMapping.setCollegeId(instituteRegistrationDetails.
			 * getInstitueRegID()); collegeCourseMapping.setCourseId(courseId);
			 * System.out.println("College ID: " +
			 * instituteRegistrationDetails.getInstitueRegID() + "\n" +
			 * "Course ID: " + courseId); updatedRows =
			 * eduService.insertCollegeCourseMappingDetails(collegeCourseMapping
			 * ); } System.out.println(
			 * "register college-Course updated rows are:" + updatedRows);
			 */

			/*
			 * List<EduKeyValue> keyValuesForInsReg = new ArrayList<>();
			 * keyValuesForInsReg.add(new EduKeyValue(Constants.LBL_OPERATION,
			 * String.valueOf(CaseConstants.MONTHLY_ENQUIRIES)));
			 * keyValuesForInsReg.add(new EduKeyValue("userId",
			 * String.valueOf((SmValidator.isIntEmpty(userId) ? 0 : userId))));
			 * keyValuesForInsReg.add(new EduKeyValue("message",
			 * "Monthly enquiries are updated."));
			 * response.sendRedirect(request.getContextPath() + Constants.LBL_CONTROLLER +
			 * EduUtils.prepareURLKeyValues(keyValuesForInsReg));
			 */

			break;

		case CaseConstants.MONTHLY_ENQUIRIES:
			monthlyEnquiryChartDatas = new ArrayList<>();
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Jan", 20));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Feb", 100));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("March", 820));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("April", 20));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("May", 20));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Jun", 20));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Jul", 40));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Aug", 55));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Sept", 60));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Oct", 70));
			monthlyEnquiryChartDatas.add(new MonthlyEnquiryChartData("Nov", 90));

			/*userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			*/
			collegeId = Integer.parseInt(request.getParameter("collegeId"));
			instituteRegistrationDetails = eduInstituteService.getInstituteRegistrationDetailsByCollegeId(collegeId);
			System.out.println("collegeId is:- " + collegeId);
			collegeDetails = eduInstituteService.getSpecificDetailsByCollegeID(collegeId);
			System.out.println("instituteRegistrationDetails:- " + instituteRegistrationDetails);
			request.setAttribute("instituteRegistrationDetails", instituteRegistrationDetails);
			request.setAttribute("monthlyEnquiryChartDatas", monthlyEnquiryChartDatas);
			request.setAttribute("collegeDetails", collegeDetails);
			
			request.getRequestDispatcher("/Institute_Index.jsp").forward(request, response);
			break;

		case CaseConstants.INSERT_STREAM_DETAILS:
			CollegeStreamMapping collegeStreamMapping = new CollegeStreamMapping();
			if(inputValidations.validationForInsertStreamDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			StreamDetails streamdetails = prepareBean.beanForInsertSreamDetails(request);
			String streamName = streamdetails.getStreamName();
			/**/
			instituteId = Integer.parseInt(request.getParameter("instituteId"));
			System.out.println("instituteId is:- " + instituteId);
			updatedRows = eduInstituteService.insertStreamDetails(streamdetails);
			System.out.println("Streams updated are:" + updatedRows);
			System.out.println("streams are: " + streamdetails);

			streamdetails = eduInstituteService.getStreamDetaillsByStreamName(streamName);

			collegeStreamMapping.setCollegeId(instituteId);
			collegeStreamMapping.setStreamId(streamdetails.getStreamId());
			eduInstituteService.insertCollegeStreamMappingDetails(collegeStreamMapping);
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			keyValues = new ArrayList<>();
			keyValues.add(new EduKeyValue(Constants.LBL_OPERATION, String.valueOf(CaseConstants.GET_STREAM_AND_COURSES)));
			keyValues.add(new EduKeyValue("instituteId",
					String.valueOf((SmValidator.isIntEmpty(instituteId) ? 0 : instituteId))));
			keyValues.add(new EduKeyValue("message", "instituteId details are updated."));
			// forward to GET_STREAM_AND_COURSES case
			response.sendRedirect(request.getContextPath() + Constants.LBL_CONTROLLER + EduUtils.prepareURLKeyValues(keyValues));
			break;

		case CaseConstants.INSERT_COURSE_DETAILS:
			CollegeCourseMapping collegeCourseMapping = new CollegeCourseMapping();
			if(inputValidations.validationForInsertCourseDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			CourseDetails coursedetails = prepareBean.beanForInsertCourseDetails(request);
			String courseName = coursedetails.getCourseName();

			instituteId = Integer.parseInt(request.getParameter("instituteId"));
			System.out.println("instuteId is :- " + instituteId);
			updatedRows = eduInstituteService.insertCourseDetails(coursedetails);
			System.out.println("Course updated are:" + updatedRows);
			System.out.println("courses are: " + coursedetails);

			coursedetails = eduInstituteService.getCourseDetailsByCourseName(courseName);

			collegeCourseMapping.setCollegeId(instituteId);
			collegeCourseMapping.setCourseId(coursedetails.getCourseId());
			eduInstituteService.insertCollegeCourseMappingDetails(collegeCourseMapping);
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			keyValues = new ArrayList<>();
			keyValues.add(new EduKeyValue(Constants.LBL_OPERATION, String.valueOf(CaseConstants.GET_STREAM_AND_COURSES)));
			keyValues.add(new EduKeyValue("instituteId",
					String.valueOf((SmValidator.isIntEmpty(instituteId) ? 0 : instituteId))));
			keyValues.add(new EduKeyValue("message", "instituteId details details are updated."));
			// forward to GET_STREAM_AND_COURSES case
			response.sendRedirect(request.getContextPath() + Constants.LBL_CONTROLLER + EduUtils.prepareURLKeyValues(keyValues));
			break;

		case CaseConstants.GET_STREAM_AND_COURSES:
			/*userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			EduInstituteDao eduInstituteDao = new EduInstituteDao();
			instituteRegistrationDetails = eduInstituteDao.getInstituteRegistrationDetailsByUserId(userId);
			instituteId = instituteRegistrationDetails.getInstitueRegID();
			 collegeId = instituteRegistrationDetails.getCollegeId();
			 collegeDetails = eduInstituteService.getSpecificDetailsByCollegeID(collegeId);
			List<CollegeCourseMapping> collegeCourseMappings = eduInstituteService
					.getStreamAndCoursesByInstituteId(instituteId);
			request.setAttribute("collegeCourseMappings", collegeCourseMappings);
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			instituteRegistrationDetails = eduInstituteService.getInstituteRegistrationDetailsByUserId(userId);
			System.out.println("instituteRegistrationDetails:- " + instituteRegistrationDetails);
			request.setAttribute("instituteRegistrationDetails", instituteRegistrationDetails);
			request.setAttribute("collegeDetails", collegeDetails);*/
			
			/*userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();*/
			collegeId = Integer.parseInt(request.getParameter("collegeId"));
			List<StreamDetails> streamList = eduInstituteService.getCollegeWiseStreams(collegeId);
			List<CourseDetails> courseList = eduInstituteService.getCollegeWiseCourse(collegeId);
			System.out.println("courseList are:- " + courseList);
			System.out.println("streamList are" + streamList);
			request.setAttribute("streamList", streamList);
			request.setAttribute("courseList", courseList);

	
			collegeDetails = eduInstituteService.getSpecificDetailsByCollegeID(collegeId);
			request.setAttribute("collegeDetails", collegeDetails);
			
			instituteRegistrationDetails = eduInstituteService.getInstituteRegistrationDetailsByCollegeId(collegeId);
			//instituteRegistrationDetails = eduInstituteService.getInstituteRegistrationDetailsByUserId(userId);
			//System.out.println("instituteRegistrationDetails:- " + instituteRegistrationDetails);
			request.setAttribute("instituteRegistrationDetails", instituteRegistrationDetails);

			request.getRequestDispatcher("/streams-and-courses.jsp").forward(request, response);

			break;

		case CaseConstants.GET_SEARCHED_STREAMS:
			String searchTerm = request.getParameter("searchTerm");
			System.out.println("********* " + searchTerm);
			streamList = eduInstituteService.getStreamsBySearchItems(searchTerm);
			System.out.println("streamList are" + streamList);
			String json = new Gson().toJson(streamList);
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			break;

		case CaseConstants.GET_SEARCHED_COURSES_BY_STREAMS:
			searchTerm = request.getParameter("searchTerm");

			System.out.println("********* " + searchTerm);
			streamName = request.getParameter("streamName");
			System.out.println("********* " + streamName);
			int streamId = Integer.parseInt(request.getParameter("streamId"));
			System.out.println("streamId is:- " + streamId);

			courseList = eduInstituteService.getCoursesByStreams(searchTerm, streamId);
			System.out.println("courseList are" + courseList);
			// List<StreamDetails> streamList =
			// eduDao.getStreamsBySearchItems(request.getParameter("searchTerm"));

			json = new Gson().toJson(courseList);
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			break;
			
		case CaseConstants.GET_SEARCHED_COURSES_BY_SPECIFIED_STREAMS:
			System.out.println("GET_SEARCHED_COURSES_BY_SPECIFIED_STREAMS case....");
			streamId = Integer.parseInt(request.getParameter("streamId"));
			if(inputValidations.validationForGetSearchedCoursesBySpecifiedStreams(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			System.out.println("streamId is:- " + streamId);

			courseList = eduInstituteService.getCoursesBySpecifiedStreams(streamId);
			System.out.println("courseList are" + courseList);
			HomePageData data = new HomePageData();
			data.setAllCourseDetails(courseList);
		//	request.setAttribute("courseList", courseList);

			json = new Gson().toJson(data);
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			/*
			request.getRequestDispatcher("/course-list.jsp").forward(request, response);*/
			/*// List<StreamDetails> streamList =
			// eduDao.getStreamsBySearchItems(request.getParameter("searchTerm"));

			json = new Gson().toJson(courseList);
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);*/
			break;

		case CaseConstants.DISPLAY_SEARCHED_ITEMS:

			System.out.println("DISPLAY_SEARCHED_ITEMS case.....");
			String course = request.getParameter("course");
			System.out.println("course is:- " + course);

			/*
			 * userDetails = getUserFromSession(request); userId =
			 * userDetails.getUserId(); instituteRegistrationDetails =
			 * eduDao.getInstituteRegistrationDetailsByUserId(userId);
			 * instituteId = instituteRegistrationDetails.getInstitueRegID();
			 * getStreamdetails =
			 * eduDao.getStreamDetailsByInstituteID(instituteId);
			 * getCoursedetails =
			 * eduDao.getCourseDetailsByInstituteID(instituteId);
			 * System.out.println("Streams in this college are:-" +
			 * getStreamdetails); System.out.println(
			 * "courses in this college are:-" + getCoursedetails );
			 * request.setAttribute("instituteId", instituteId);
			 * request.setAttribute("streamdetails", getStreamdetails);
			 * request.setAttribute("coursedetails", getCoursedetails);
			 */
			request.getRequestDispatcher("/show-streams-and-courses.jsp").forward(request, response);
			break;

		case CaseConstants.SEARCH_COURSES_FOR_STUDENTS:
			System.out.println("SEARCH_COURSES_FOR_STUDENTS CASE");
			searchTerm = request.getParameter("searchTerm");
			System.out.println("********* " + searchTerm);
			/*
			 * List<CatagoryDetails> list =
			 * operations.getItemCategoriesBySearchterm(request.getParameter(
			 * "searchTerm"));
			 */
			courseList = eduInstituteService.getCoursesBySearchItems(searchTerm);
			System.out.println("courseList are" + courseList);
			// List<StreamDetails> streamList =
			// eduDao.getStreamsBySearchItems(request.getParameter("searchTerm"));
			/*
			 * SearchItemData searchItemData = new SearchItemData();
			 * searchItemData.setAllCourseDetailsForSearch(courseList);
			 * searchItemData.setAllStreamDetailsForSearch(streamList);
			 */
			json = new Gson().toJson(courseList);
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			break;

		case CaseConstants.SEARCH_CITY:
			System.out.println("SEARCH_CITY case");
			searchTerm = request.getParameter("searchTerm");
			System.out.println("********* " + searchTerm);
			/*
			 * List<CatagoryDetails> list =
			 * operations.getItemCategoriesBySearchterm(request.getParameter(
			 * "searchTerm"));
			 */
			List<CityDetails> cityList = eduInstituteService.getCityBySearchItems(searchTerm);
			System.out.println("cityList are" + cityList);
			// List<StreamDetails> streamList =
			// eduDao.getStreamsBySearchItems(request.getParameter("searchTerm"));
			/*
			 * SearchItemData searchItemData = new SearchItemData();
			 * searchItemData.setAllCourseDetailsForSearch(courseList);
			 * searchItemData.setAllStreamDetailsForSearch(streamList);
			 */
			json = new Gson().toJson(cityList);
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			break;

		case CaseConstants.RETRIEVE_INDEX_DATA:
			System.out.println("THIS IS INDEX PAGE DATA");
			// String dataForIndex = "TEST DATA FOR INDEX";
			// request.setAttribute("dataForIndex", dataForIndex);

			/*List<InstituteRegistrationDetails> allInstituteDetails = eduInstituteService.getAllInstituteRegestrationDetails();
			System.out.println("all Institute Details are:- " + allInstituteDetails);
			*/
			List<FeaturedColleges> allInstituteDetails = eduInstituteService.getAllFeaturedCollegeDetails();
			System.out.println("all Institute Details are:- " + allInstituteDetails);
			
			
			/*List<CollegeDetails> allCollegeDetails= eduInstituteService.gelAllCollegeDetails();
			System.out.println("allCollegeDetails:- " + allCollegeDetails);
			*//*
			 * List<StreamDetails> allStreamDetails =
			 * eduService.getAllInstituteStreamsDetails(); System.out.println(
			 * "all Stream Details :- " + allStreamDetails);
			 * 
			 * List<CourseDetails> allCourseDetails =
			 * eduService.getAllInstituteCourseDetails(); System.out.println(
			 * "all Course Details :- " + allCourseDetails);
			 */
			data = new HomePageData();

			//data.setAllInstituteDetails(allInstituteDetails);
			data.setFeaturedColleges(allInstituteDetails);
		//	data.setAllCollegeDetails(allCollegeDetails);
			/*
			 * data.setAllStreamDetails(allStreamDetails);
			 * data.setAllCourseDetails(allCourseDetails);
			 */

			json = new Gson().toJson(data);
			System.out.println(json);
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			break;

		case CaseConstants.EDIT_STUDENT_PROFILE_DETAILS:
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			/* USER PROFILE DATA START */
			userDetails = EduService.getProfileDataOfUser(userId);
			studentDetails = eduStudentService.getStudentDetailsByUserId(userId);
			System.out.println("Getting student details by userId:-" + studentDetails);
			request.setAttribute("userDetails", userDetails);
			request.setAttribute("studentDetails", studentDetails);
			/* USER PROFILE DATA END */
			request.getRequestDispatcher("/edit-student-profile.jsp").forward(request, response);
			break;

		case CaseConstants.UPDATE_STUDENT_PROFILE_DETAILS:
			System.out.println("This is Update Profile Details Case");
			/*
			 * userDetails = getUserFromSession(request); userId =
			 * userDetails.getUserId(); userDetails =
			 * EduService.getProfileDataOfUser(userId); String OldPasswordFromDB
			 * = userDetails.getPassword(); eduService.
			 */
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			if(inputValidations.validationForUpdateStudentProfileDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			userDetails = prepareBean.beanForUpdateProfileDetails(request);
			userDetails.setUserId(userId);

			updatedRows = eduStudentService.updateProfileDetails(userDetails);
			System.out.println("Updated Rows are :- " + updatedRows);

			keyValues = new ArrayList<>();
			keyValues.add(new EduKeyValue(Constants.LBL_OPERATION, String.valueOf(CaseConstants.USER_PROFILE)));
			keyValues.add(new EduKeyValue("userId", String.valueOf((SmValidator.isIntEmpty(userId) ? 0 : userId))));
			keyValues.add(new EduKeyValue("message", "userDetails are updated."));
			response.sendRedirect(request.getContextPath() + Constants.LBL_CONTROLLER + EduUtils.prepareURLKeyValues(keyValues));

			break;

		/*
		 * case CaseConstants.INSERT_INSTITUTE_LEADS: InstituteLeads
		 * instituteLeads = prepareBean.beanForInsertLeads(request); updatedRows
		 * = eduService.insertInstituteLeads(instituteLeads); List<EduKeyValue>
		 * keyValuesForInsReg = new ArrayList<>(); keyValuesForInsReg.add(new
		 * EduKeyValue(Constants.LBL_OPERATION,
		 * String.valueOf(CaseConstants.MONTHLY_ENQUIRIES)));
		 * keyValuesForInsReg.add(new EduKeyValue("instituteId",
		 * String.valueOf((SmValidator.isIntEmpty(instituteLeads.getInstituteId(
		 * )) ? 0 : instituteLeads.getInstituteId()))));
		 * keyValuesForInsReg.add(new EduKeyValue("message",
		 * "Monthly Enquires are updated."));
		 * response.sendRedirect(request.getContextPath() + Constants.LBL_CONTROLLER +
		 * EduUtils.prepareURLKeyValues(keyValuesForInsReg)); break;
		 */

		case CaseConstants.GET_INSTITUTE_LEADS:
			collegeId = Integer.parseInt(request.getParameter("collegeId"));
			//instituteRegistrationDetails = eduInstituteService.getInstituteRegistrationDetailsByCollegeId(collegeId);
			//instituteId = instituteRegistrationDetails.getInstitueRegID();
			List<InstituteLeads> instituteLeadsList = eduInstituteService.getRegisteredInstituteLeadsByCollegeId(collegeId);
			collegeDetails = eduInstituteService.getSpecificDetailsByCollegeID(collegeId);
			request.setAttribute("collegeDetails", collegeDetails);
		/*	System.out.println("instituteRegistrationDetails:- " + instituteRegistrationDetails);
			request.setAttribute("instituteRegistrationDetails", instituteRegistrationDetails);
		*/	request.setAttribute("instituteLeadsList", instituteLeadsList);

			request.getRequestDispatcher("/institute-leads.jsp").forward(request, response);
			/*
			 * 
			 * List<RegisteredInstituteLeads> registeredinstituteleads =
			 * eduInstituteService.getRegisteredInstituteLeadsByInstituteId(
			 * instituteId); StreamDetails getStreamdetail =
			 * eduInstituteService.getSingleStreamDetailsByInstituteID(
			 * instituteId); CourseDetails getCoursedetail =
			 * eduInstituteService.getSingleCourseDetailsByInstituteID(
			 * instituteId); System.out.println("Getting Stream Details :- " +
			 * getStreamdetail); System.out.println("Getting Course Details:- "
			 * + getCoursedetail); request.setAttribute("getStreamdetail",
			 * getStreamdetail); request.setAttribute("getCoursedetail",
			 * getCoursedetail);
			 * //request.setAttribute("instituteRegistrationDetails",
			 * instituteRegistrationDetails);
			 * request.setAttribute("registeredinstituteleads",
			 * registeredinstituteleads);
			 */
			break;

		case CaseConstants.INSERT_INSTITUTE_REVIEW:
			System.out.println("Its Institute Review");
			if(inputValidations.validationForInsertInstituteReview(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			InstituteReviews institutereviews = prepareBean.beanForInstituteReviews(request);
			System.out.println("review data is :- " + institutereviews);
			updatedRows = eduInstituteService.insertInstituteReviews(institutereviews);
			System.out.println("Reviews are as follows :-" + updatedRows);
			break;

		case CaseConstants.HOME_SEARCH_RESULT:
			System.out.println("This is search result case.......");
			int courseId = Integer.parseInt(request.getParameter("courseId"));
			courseName = request.getParameter("course");
			String city = request.getParameter("city");
			if(!SmValidator.isIntEmpty(courseId) || !SmValidator.isStringEmpty(city))
			{
				
			System.out.println("courseId is:- " + courseId + "\n" + "searched city term is:- " + city + "\ncourseName:-" + courseName);
			
			List<CollegeDetails> allCollegeListWithDetails = eduService.getInstituteDetailsByCourseIdAndCity(courseId, city,courseName);
			System.out.println("collegeDetails from search:- " + allCollegeListWithDetails);
			String courseName2= null;
			for (CollegeDetails collegeDetail : allCollegeListWithDetails) {
				courseName2 = collegeDetail.getCourseDetails().getCourseName();
			}
			System.out.println("courseName is:- " + courseName2);
			request.setAttribute("courseName2", courseName2);
			request.setAttribute("allCollegeListWithDetails", allCollegeListWithDetails);
			request.getRequestDispatcher("/institute-list-home-search.jsp").forward(request, response);
			}else{
				request.setAttribute("homeSearchMsg", "Enter Valid Stream and Course.");
				request.getRequestDispatcher("/institute-list-home-search.jsp").forward(request, response);
				return;
			}
			/*instituteRegistrationDetails = new InstituteRegistrationDetails();
			collegeCourseMapping = new CollegeCourseMapping();
			collegeCourseMapping.setCourseId(courseId);
			instituteRegistrationDetails.setInstituteCity(city);
			
			List<InstituteRegistrationDetails> registrationDetails = eduService
					.getInstituteDetailsByHomeSearch(instituteRegistrationDetails, collegeCourseMapping);
			System.out.println("getInstituteDetailsByHomeSearch are:- " + registrationDetails);
			request.setAttribute("registrationDetails", registrationDetails);*/
			
			
			break;

		case CaseConstants.GET_SINGLE_STREAM_DETAILS:
			System.out.println("GET_SINGLE_STREAM_DETAILS case.....");
			/*streamName = request.getParameter("streamName");
			System.out.println("streamName is:- " + streamName);
			request.getRequestDispatcher("/edu-stream-details.jsp").forward(request, response);
			;*/
			
			streamId = Integer.parseInt(request.getParameter("streamId"));
			System.out.println("Stream Id is:- " + streamId);
			if(inputValidations.validationForGetSingleStreamDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			
			SpecificStreamInfo specificStreamInfo = eduService.getSpecificStreamInfoByStreamId(streamId);
			System.out.println("specificStreamInfo:- " + specificStreamInfo);
			request.setAttribute("specificStreamInfo", specificStreamInfo);
			
			messages = eduService.getAllMessage();
			System.out.println("messages are:- " + messages);
			request.setAttribute("messages", messages);
			
			request.getRequestDispatcher("/edu-stream-details.jsp").forward(request, response);
			
			break;

		/*
		 * case CaseConstants.INSERT_COUNSELLOR_PROFILE_DETAILS:
		 * System.out.println("This is Counnsellor Profile");
		 * CounsellorProfileDetails counsellorProfiledetails =
		 * prepareBean.beanForInsertCounsellorProfileDetails(request);
		 * userDetails = getUserFromSession(request); userId =
		 * userDetails.getUserId(); counsellorProfiledetails.setUserId(userId);
		 * updatedRows = eduCounsellorService.insertCounsellorProfileDetails(
		 * counsellorProfiledetails); System.out.println(
		 * "Counsellor Profile updated rows are:" + updatedRows);
		 * request.setAttribute("counsellorProfiledetails",
		 * counsellorProfiledetails);
		 * request.getRequestDispatcher("/CounselorProfile.jsp").forward(
		 * request, response); break;
		 */

		case CaseConstants.GET_COUNSELLOR_PROFILE_DETAILS:

			CounsellorProfileDetails counsellorProfiledetails = new CounsellorProfileDetails();
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();

			counsellorProfiledetails = eduCounsellorService.getCounsellorProfileDetailsByUserId(userId);
			// counsellorProfileId =
			// counsellorProfiledetails.getCounsellor_profile_details_id();

			System.out.println("Details to be inserted are:- " + counsellorProfiledetails);

			// USER PROFILE DATA START
			request.setAttribute("userDetails", userDetails);
			request.setAttribute("counsellorProfileDetails", counsellorProfiledetails);
			// USER PROFILE DATA END

			request.getRequestDispatcher("/counsellor-myProfile.jsp").forward(request, response);
			break;

		case CaseConstants.INSERT_COUNSELLOR_EDUCATION_BACKGROUND:
			if(inputValidations.validationForInsertCounsellorEducationBackground(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			List<CounsellorEducationalBackground> counselloreducationalbackground = prepareBean
					.beanForInsertCounsellorEducationalBackground(request);
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			updatedRows = 0;
			userDetails = EduService.getProfileDataOfUser(userDetails.getUserId());
			counsellorProfiledetails = eduCounsellorService
					.getCounsellorProfileDetailsByUserId(userDetails.getUserId());
			System.out.println("Getting counsellor profile details by userId:-" + counsellorProfiledetails);
			request.setAttribute("userDetails", userDetails);
			request.setAttribute("counsellorProfiledetails", counsellorProfiledetails);

			CounsellorProfileDetails counsellorprofiledetails = eduCounsellorService
					.getCounsellorProfileDetailsByUserId(userId);
			for (CounsellorEducationalBackground counsellorEducationalBackground : counselloreducationalbackground) {
				counsellorEducationalBackground
						.setCounsellor_profile_details_id(counsellorprofiledetails.getCounsellor_profile_details_id());
				updatedRows = eduCounsellorService
						.insertCounsellorEducationalBackground(counsellorEducationalBackground);
			}
			System.out.println("updated rows are:- " + updatedRows);
			request.getRequestDispatcher("/add-counsellor-edu-bg.jsp").forward(request, response);

			break;

		
		  case CaseConstants.GET_COUNSELLOR_EDUCATION_BACKGROUND:
		  counsellorprofiledetails = new CounsellorProfileDetails();
		  userDetails = getUserFromSession(request); 
		  userId = userDetails.getUserId(); 
		  counsellorprofiledetails = eduCounsellorService.getCounsellorProfileDetailsByUserId(userId);
		  int counsellor_profile_details_id = counsellorprofiledetails.getCounsellor_profile_details_id();
		  List<CounsellorEducationalBackground> allCounsellorEducationalBackground = eduCounsellorService.getCounsellorEduBgByCounsellorProfileId(counsellor_profile_details_id);
		  System.out.println("edubgdetail to be insertedis:- " + allCounsellorEducationalBackground);
		  
		  userDetails = EduService.getProfileDataOfUser(userId);
		  System.out.println("Getting student details by userId:-" + counsellorprofiledetails); 
		  request.setAttribute("userDetails",userDetails); 
		  request.setAttribute("counsellorprofiledetails",counsellorprofiledetails);
		  
		  request.setAttribute("allCounsellorEducationalBackground", allCounsellorEducationalBackground);
		  
		  
		  request.getRequestDispatcher("/showAllCounsellorEduBG.jsp").forward(request, response); 
		  break;
		  
		  /*case CaseConstants.EDIT_COUNSELLOR_EDUCATION_BACKGROUND: 
		  int counsellor_educational_background_id = Integer.parseInt(request.getParameter("counsellor_educational_background_id")); 
		  System.out.println("counsellor_educational_background_id is :- " + counsellor_educational_background_id);
		  counselloreducationalbackground = eduCounsellorService.getCounsellorEduBgBycounsellor_educational_background_id(counsellor_educational_background_id); 
		  System.out.println("counselloreducationalbackgrounds are:- " +counselloreducationalbackground); 
		  userDetails =getUserFromSession(request); 
		  userId = userDetails.getUserId();
		  userDetails = EduService.getProfileDataOfUser(userId);
		  counsellorprofiledetails = eduCounsellorService.getCounsellorProfileDetailsByUserId(userId);
		  System.out.println("Getting student details by userId:-" +counsellorprofiledetails); 
		  request.setAttribute("userDetails",userDetails); 
		  request.setAttribute("counsellorprofiledetails",counsellorprofiledetails);
		  
		  request.setAttribute("counselloreducationalbackground",counselloreducationalbackground);
		  request.getRequestDispatcher("/edit-counsellor-edu-bg.jsp").forward(request, response); 
		  break;
		  
		  case CaseConstants.UPDATE_COUNSELLOR_EDUCATION_BACKGROUND:
		  counsellor_educational_background_id = Integer.parseInt(request.getParameter("counsellor_educational_background_id"));
		  CounsellorEducationalBackground counselloreducationalbackgroundBean = prepareBean.beanForUpdateSingleCounsellorEduBgBycounsellor_educational_background_id(counsellor_educational_background_id,request);
		  counselloreducationalbackgroundBean.setCounsellor_educational_background_id(counsellor_educational_background_id); 
		  updatedRows =eduCounsellorService.updateCounsellorEduBgBycounsellor_educational_background_id(counsellor_educational_background_id,counselloreducationalbackgroundBean);
		  
		  break;
		  */
		  case CaseConstants.INSERT_COUNSELLOR_WORK_EXPERIENCE:
			  if(inputValidations.validationForInsertCounsellorWorkExperience(request))
				{
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
			List<CounsellorWorkExperience> counsellorworkexperience = prepareBean.beanForInsertCounsellorWorkExp(request);
			System.out.println("Counsellor Work exp details are: - " + counsellorworkexperience);
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();
			updatedRows = 0;
			userDetails = EduService.getProfileDataOfUser(userDetails.getUserId());
			counsellorProfiledetails = eduCounsellorService.getCounsellorProfileDetailsByUserId(userDetails.getUserId());
			System.out.println("Getting counsellor profile details by userId:-" + counsellorProfiledetails);
			request.setAttribute("userDetails", userDetails);
			request.setAttribute("counsellorProfiledetails", counsellorProfiledetails);
			counsellorProfiledetails = eduCounsellorService.getCounsellorProfileDetailsByUserId(userId);
			
			for(CounsellorWorkExperience counsellorworkexp: counsellorworkexperience) 
			{
				counsellorworkexp.setCounsellor_profile_details_id(counsellorProfiledetails.getCounsellor_profile_details_id());
				updatedRows = eduCounsellorService.insertCounsellorWorkExp(counsellorworkexp);
			}
			System.out.println("updated rows are:- " + updatedRows);
			request.getRequestDispatcher("/add-counsellor-work-exp.jsp").forward(request, response);
			break;
			
		  case CaseConstants.GET_COUNSELLOR_WORK_EXPERIENCE:
			  counsellorprofiledetails = new CounsellorProfileDetails();
			  userDetails = getUserFromSession(request); 
			  userId = userDetails.getUserId();
			  counsellorprofiledetails = eduCounsellorService.getCounsellorProfileDetailsByUserId(userId);
			  counsellor_profile_details_id = counsellorprofiledetails.getCounsellor_profile_details_id();
			  List<CounsellorWorkExperience> allCounsellorWorkExperience = eduCounsellorService.getCounsellorWorkExperienceByCounsellorProfileDetailsId(counsellor_profile_details_id);
			  System.out.println("Counsellor's Work Exp to be inserted is:- " + allCounsellorWorkExperience);
			  
			  userDetails = EduService.getProfileDataOfUser(userId);
			  System.out.println("Getting counsellor profile details by userId:-" + counsellorprofiledetails);
			  request.setAttribute("userDetails", userDetails);
			  request.setAttribute("counsellorprofiledetails", counsellorprofiledetails);

			  request.setAttribute("allCounsellorWorkExperience", allCounsellorWorkExperience);
			  request.getRequestDispatcher("/showAllWorkExpDetails.jsp").forward(request, response);

			  break;
			
		 case CaseConstants.INSERT_COUNSELLOR_PREFERENCES:
			 if(inputValidations.validationForInsertCounsellorPreferences(request))
				{
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
			 List<CounsellorPreferences> counsellorpreferences = prepareBean.beanForInsertCounsellorPreferences(request);
				System.out.println("counsellor Preferences details are : " + counsellorpreferences);
				userDetails = getUserFromSession(request);
				userId = userDetails.getUserId();
				updatedRows = 0;

				for (CounsellorPreferences counsellorpreference : counsellorpreferences) {
					counsellorProfiledetails = eduCounsellorService.getCounsellorProfileDetailsByUserId(userId);
					counsellor_profile_details_id = counsellorProfiledetails.getCounsellor_profile_details_id();
					counsellorpreference.setCounsellor_profile_details_id(counsellor_profile_details_id);
					updatedRows = eduCounsellorService.insertCounsellorPreferences(counsellorpreference);
				}
				System.out.println("updated rows are:- " + updatedRows);
				request.getRequestDispatcher("/add-edu-pref.jsp").forward(request, response);
				break;
				
		 case CaseConstants.GET_COUNSELLOR_PREFERENCES:
			 counsellorprofiledetails = new CounsellorProfileDetails();
			 userDetails = getUserFromSession(request);
			 userId = userDetails.getUserId();
			 counsellorprofiledetails = eduCounsellorService.getCounsellorProfileDetailsByUserId(userId);
			 counsellor_profile_details_id = counsellorprofiledetails.getCounsellor_profile_details_id();
			 
			 List<CounsellorPreferences> allCounsellorPreferences = eduCounsellorService.getCounsellorPreferencesById(counsellor_profile_details_id);
			 System.out.println("CounsellorPreferences inserted are : " + allCounsellorPreferences);
			 request.setAttribute("allCounsellorPreferences", allCounsellorPreferences);

			 request.getRequestDispatcher("/showAllEduPrefDetails.jsp").forward(request, response);
			 
			 break;
		
			

		case CaseConstants.INSERT_QAFORUM_DETAILS:
			System.out.println("INSERT_QAFORUM_DETAILS CASE.........");
			userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();

			String msg = request.getParameter("message");

			int parentMsgId = Integer.parseInt(request.getParameter("parentMsgId"));
			System.out.println("msg:- " + msg + "\n" + "parentmsgid:- " + parentMsgId);
			if(inputValidations.validationForInsertQAForumDetails(request))
			{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			QAForumDetails qaForumDetails = prepareBean.beanForinsertingQAForumdetails(request, userId);
			updatedRows = eduService.insertingQAForumdetails(qaForumDetails);
			System.out.println("updatedRows are:- " + updatedRows);
			keyValues = new ArrayList<>();
			keyValues.add(new EduKeyValue(Constants.LBL_OPERATION, String.valueOf(CaseConstants.GET_QAFORUM_DETAILS)));
			keyValues.add(new EduKeyValue("userId", String.valueOf((SmValidator.isIntEmpty(userId) ? 0 : userId))));
			keyValues.add(new EduKeyValue("message", "Qusetion Asked."));
			response.sendRedirect(request.getContextPath() + Constants.LBL_CONTROLLER + EduUtils.prepareURLKeyValues(keyValues));

			break;
			
		/*case CaseConstants.GET_QAFORUM_DETAILS_IN_STREAMINFO:
			List<QAForumDetails> messages = eduService.getAllMessage();
			System.out.println("messages are:- " + messages);
			request.setAttribute("messages", messages);
			request.getRequestDispatcher("/instituteQuestionAnswerForum.jsp").forward(request, response);
			break;*/
		case CaseConstants.GET_QAFORUM_DETAILS:
			userDetails = getUserFromSession(request);
			String userrole = userDetails.getUserRole();
			System.out.println("userrole is:- " + userrole);
			messages = eduService.getAllMessage();
			System.out.println("messages are:- " + messages);
			request.setAttribute("messages", messages);

			if (userrole.equals(Constants.USER_ROLE_STUDENT)) {
				/* USER PROFILE DATA START */
				userDetails = getUserFromSession(request);
				userId = userDetails.getUserId();
				userDetails = EduService.getProfileDataOfUser(userId);
				studentDetails = eduStudentService.getStudentDetailsByUserId(userId);
				System.out.println("Getting student details by userId:-" + studentDetails);
				request.setAttribute("userDetails", userDetails);
				request.setAttribute("studentDetails", studentDetails);
				/* USER PROFILE DATA END */
				request.getRequestDispatcher("/studentQuestionAnswerForum.jsp").forward(request, response);
			} else if (userrole.equals(Constants.USER_ROLE_INSTITUTE)) {
				userDetails = getUserFromSession(request);
				userId = userDetails.getUserId();
				instituteRegistrationDetails = eduInstituteService.getInstituteRegistrationDetailsByUserId(userId);
				collegeId = instituteRegistrationDetails.getCollegeId();
				collegeDetails = eduInstituteService.getSpecificDetailsByCollegeID(collegeId);
				request.setAttribute("collegeDetails", collegeDetails);

				System.out.println("instituteRegistrationDetails:- " + instituteRegistrationDetails);
				request.setAttribute("instituteRegistrationDetails", instituteRegistrationDetails);

				request.getRequestDispatcher("/instituteQuestionAnswerForum.jsp").forward(request, response);
			} else if (userrole.equals(Constants.USER_ROLE_COUNSELLOR)) {
				request.getRequestDispatcher("/counsellorQuestionAnswerForum.jsp").forward(request, response);
			}
			break;
			
		case CaseConstants.GET_INSTITUTE_LIST_WITH_DETAILS:
			
			List<CollegeDetails> allCollegeListWithDetails = eduInstituteService.gelAllCollegeDetails();
			System.out.println("allCollegeListWithDetails are:- " + allCollegeListWithDetails);
			request.setAttribute("allCollegeListWithDetails", allCollegeListWithDetails);
			request.getRequestDispatcher("/institute-list.jsp").forward(request, response);
			break;
			
		case CaseConstants.GET_SPECIFIC_COLLEGE_DETAILS:
			System.out.println( "GET_SPECIFIC_COLLEGE_DETAILS ");
			collegeId = Integer.parseInt(request.getParameter("collegeId"));
			System.out.println("this is college id :- " + collegeId);
			collegeDetails = eduInstituteService.getSpecificDetailsByCollegeID(collegeId);
			System.out.println(collegeDetails);
			request.setAttribute("collegeDetails", collegeDetails);
			request.getRequestDispatcher("/specific-college-details.jsp").forward(request, response);
			
			break;
			
		case CaseConstants.LOGOUT:
			System.out.println("This is logout case");
			HttpSession sessionForLogout = request.getSession(false);
			sessionForLogout.removeAttribute(Constants.LBL_USERDETAILS);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			break;

		/*
		 * case CaseConstants.UPDATE_COUNSELLOR_PROFILE_DETAILS:
		 * counsellorProfileId =
		 * Integer.parseInt(request.getParameter("counsellorProfileId"));
		 * CounsellorProfileDetails counsellorProfileDetail =
		 * prepareBean.beanForUpdateCounsellorDetails(request); userDetails =
		 * getUserFromSession(request); userId = userDetails.getUserId();
		 * userDetails =
		 * EduService.getProfileDataOfUser(userDetails.getUserId());
		 * counsellorProfiledetails =
		 * eduCounsellorService.getCounsellorProfileDetailsByUserId(userId);
		 * counsellorProfileId =
		 * counsellorProfiledetails.getCounsellorProfileId(); updatedRows =
		 * eduCounsellorService.UpdateCounsellorProfileDetailsById(
		 * counsellorProfileId, counsellorProfileDetail);
		 * 
		 * keyValues = new ArrayList<>(); keyValues.add(new
		 * EduKeyValue(Constants.LBL_OPERATION,
		 * String.valueOf(CaseConstants.USER_PROFILE))); keyValues.add(new
		 * EduKeyValue("counsellorProfileId",
		 * String.valueOf((SmValidator.isIntEmpty(counsellorProfileId) ? 0 :
		 * counsellorProfileId)))); keyValues.add(new EduKeyValue("message",
		 * "counsellorProfileDetails details are updated."));
		 * response.sendRedirect(request.getContextPath() + Constants.LBL_CONTROLLER +
		 * EduUtils.prepareURLKeyValues(keyValues)); break;
		 */
			
		case CaseConstants.INSERT_CONTACTUS_DETAILS:
			ContactDetails contactDetails = new ContactDetails();
			 contactDetails.setName(request.getParameter("name"));
			 contactDetails.setEmail(request.getParameter("email"));
			 contactDetails.setSubject(request.getParameter("subject"));
			 contactDetails.setContact(request.getParameter("contact"));
			 contactDetails.setMessage(request.getParameter("message"));
			
			 updatedRows = eduService.insertContactUsdetails(contactDetails); 
			 System.out.println("Updated Rows are:-" +updatedRows);
			 request.getRequestDispatcher("/contact.jsp").forward(request, response);
			 
			break;
			
		case CaseConstants.SHOW_INSTITUTE_PROFILE_DETAILS:
			collegeId = Integer.parseInt(request.getParameter("collegeId"));
			System.out.println("collegeId is:- " + collegeId);
			/*userDetails = getUserFromSession(request);
			userId = userDetails.getUserId();*/
			instituteRegistrationDetails = eduInstituteService.getInstituteRegistrationDetailsByCollegeId(collegeId);
			/*collegeId = instituteRegistrationDetails.getCollegeId();*/
			System.out.println("collegeId is:- " + collegeId);
			collegeDetails = eduInstituteService.getSpecificDetailsByCollegeID(collegeId);
			System.out.println("instituteRegistrationDetails:- " + instituteRegistrationDetails);
			request.setAttribute("instituteRegistrationDetails", instituteRegistrationDetails);
			request.setAttribute("collegeDetails", collegeDetails);
			System.out.println("collegeDetails is:- " + collegeDetails);
			request.getRequestDispatcher("/institute-profile.jsp").forward(request, response);
			
			break;
			
		case CaseConstants.GET_ALL_STREAMS_LIST:
			 streamList = eduService.getAllInstituteStreamsDetails();
			 System.out.println("streamList are" + streamList);
				request.setAttribute("streamList", streamList);
				request.getRequestDispatcher("/all-courses.jsp").forward(request, response);
			break;
			
		case CaseConstants.GET_COLLEGEWISE_STREAM_AND_COURSES:
			collegeId = Integer.parseInt(request.getParameter("collegeId"));
			streamList = eduInstituteService.getCollegeWiseStreams(collegeId);
			courseList = eduInstituteService.getCollegeWiseCourse(collegeId);
			System.out.println("courseList are:- " + courseList);
			System.out.println("streamList are" + streamList);
			request.setAttribute("streamList", streamList);
			request.setAttribute("courseList", courseList);
			request.getRequestDispatcher("/course-list.jsp").forward(request, response);
			
			break;
				
		case CaseConstants.GET_ALL_COURSES_LIST:
			
			streamList = eduInstituteService.getStreamAndCourses();
			System.out.println("streamList are" + streamList);
			request.setAttribute("streamList", streamList);
				/*request.getRequestDispatcher("/course-list.jsp").forward(request, response);*/
			request.getRequestDispatcher("/all-courses.jsp").forward(request, response);
				 
			break;
			
		case CaseConstants.GET_SINGLE_COURSE_DETAILS:
			courseId = Integer.parseInt(request.getParameter("courseId"));
			SpecificCourseInfo courseInfo= eduService.getSpecificCourseDetailsByCourseId(courseId);
			System.out.println("courseInfo are:- " + courseInfo);
			request.setAttribute("courseInfo", courseInfo);
			request.getRequestDispatcher("/edu-course-details.jsp").forward(request, response);
			break;
			
		case CaseConstants.DOWNLOAD_COLLEGE_DOCUMENTS:
			
			String filename = request.getParameter("filename");
			collegeId = Integer.parseInt(request.getParameter("collegeId"));
			System.out.println("filename:- "+ filename + "\n" + "collegeid:-" + collegeId);
			
			response.setContentType("text/html");  
			PrintWriter out = response.getWriter();  
			
			response.setContentType("APPLICATION/OCTET-STREAM"); 
			
			/*String filepath = Constants.APP_DATA_LOCATION + File.separator + Constants.COLLEGE + File.separator
					+ collegeId + File.separator + Constants.COLLEGE_OTHER_IMAGES + File.separator + filename;*/
			String filepath = Constants.APP_DATA_LOCATION + File.separator + Constants.COLLEGE + File.separator
					+ collegeId + File.separator + Constants.COLLEGE_OTHER_IMAGES + File.separator;
			
			System.out.println("filepath:- " + filepath);
			response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");   

			FileInputStream fileInputStream = new FileInputStream(filepath + filename);  

			int i;   
			while ((i=fileInputStream.read()) != -1) {  
				out.write(i);   
			}   
			fileInputStream.close();   
			out.close();   
		

		break;
		
		case CaseConstants.GET_INSTITUTES_BY_STREAMS:
			System.out.println("GET_INSTITUTES_BY_STREAMS");
			streamId = Integer.parseInt(request.getParameter("streamId"));
			System.out.println("streamId is" + streamId);
			allCollegeListWithDetails = eduService.getCollegeDetailsByStreamId(streamId);
			System.out.println("collegelist is " + allCollegeListWithDetails);
			request.setAttribute("allCollegeListWithDetails", allCollegeListWithDetails);
			request.getRequestDispatcher("/institute-list.jsp").forward(request, response);
			break;
		
		default:
			System.out.println("Invalid Operation");
			RequestDispatcher rs = request.getRequestDispatcher("/error.jsp");
			rs.forward(request, response);
			break;
		
	}

}
	

	private boolean isCounsellorProfileSubmitted(CounsellorProfileDetails counsellorProfileDetails) {
		if (!StringUtils.isNullOrEmpty(counsellorProfileDetails.getAadhar_number())
				|| !StringUtils.isNullOrEmpty(counsellorProfileDetails.getCounsellor_profile_pic_name())
				|| !StringUtils.isNullOrEmpty(counsellorProfileDetails.getQualification())
				|| !StringUtils.isNullOrEmpty(counsellorProfileDetails.getExpertise_stream())) {

			return true;
		}
		return false;
	}

	private boolean isCollegeRegistered(InstituteRegistrationDetails instituteRegistrationDetails) {

		if (!StringUtils.isNullOrEmpty(instituteRegistrationDetails.getInstituteFbName())
				|| !StringUtils.isNullOrEmpty(instituteRegistrationDetails.getInstituteCity())
				|| !StringUtils.isNullOrEmpty(instituteRegistrationDetails.getInstituteState())) {

			return true;
		}

		return false;
	}

	private boolean isValidSession(HttpServletRequest request, String operation) {
		HttpSession session = null;

		if (operation.equals( CaseConstants.RENDER_COLLEGE_PROFILE_PIC))
			return true;
		if (operation.equals( CaseConstants.GET_SPECIFIC_COLLEGE_DETAILS))
			return true;
		if (operation.equals( CaseConstants.GET_INSTITUTE_LIST_WITH_DETAILS))
			return true;
		if (operation.equals( CaseConstants.INSERT_CONTACTUS_DETAILS))
			return true;
		if (operation.equals( CaseConstants.RENDER_INSTITUTE_PROFILE_PIC))
			return true;
		if (operation.equals( CaseConstants.INSERT_INSTITUTE_LEADS))
			return true;
		if (operation.equals( CaseConstants.HOME_SEARCH_RESULT))
			return true;
		if (operation.equals( CaseConstants.INSERT_INSTITUTE_REVIEW))
			return true;
		if (operation.equals( CaseConstants.INSERT_USER_DETAILS))
			return true;
		if (operation.equals( CaseConstants.RETRIEVE_INDEX_DATA))
			return true;
		if (operation.equals( CaseConstants.GET_SINGLE_STREAM_DETAILS))
			return true;
		if (operation.equals(CaseConstants.LOGIN))
			return true;
		if (operation.equals( CaseConstants.USER_PROFILE))
			return true;
		if (operation.equals( CaseConstants.GET_SINGLE_INSTITUTE_DETAILS))
			return true;
		if (operation.equals( CaseConstants.SEARCH_COURSES_FOR_STUDENTS))
			return true;
		if (operation.equals( CaseConstants.EMAIL_VERIFICATION))
			return true;
		if (operation.equals( CaseConstants.DISPLAY_SEARCHED_ITEMS))
			return true;
		if (operation.equals( CaseConstants.SEARCH_CITY))
			return true;

		if (operation.equals( CaseConstants.MONTHLY_ENQUIRIES))
			return true;
		if (operation.equals( CaseConstants.GET_SEARCHED_COURSES_BY_STREAMS))
			return true;
		if (operation.equals( CaseConstants.GET_SEARCHED_COURSES_BY_SPECIFIED_STREAMS))
			return true;
		if (operation.equals( CaseConstants.GET_SEARCHED_STREAMS))
			return true;
		
		session = request.getSession(false);

		if (session == null)
			return false;

		Object obj = session.getAttribute(Constants.LBL_USERDETAILS);
		if (obj == null) {
			return false;
		}
		UserDetails userDetails = (UserDetails) obj;
		if (userDetails == null || userDetails.getUserId() == 0) { // this condn
																	// checking
																	// wether
																	// any user
																	// loged in
																	// or not
			return false;
		}
		return true;

	}

	private UserDetails getUserFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null)
			return null;
		UserDetails userDetails = (UserDetails) session.getAttribute(Constants.LBL_USERDETAILS);
		return userDetails;
	}

	// he 4 fileds compulsary ahet profile details sathi as consider
	private boolean isProfileDetailsSubmitted(StudentDetails studentDetails) {
		if (!StringUtils.isNullOrEmpty(studentDetails.getStudent_stream())
				|| !StringUtils.isNullOrEmpty(studentDetails.getStudent_course())
				|| !StringUtils.isNullOrEmpty(studentDetails.getStudent_mode_of_study())
				|| !StringUtils.isNullOrEmpty(studentDetails.getStudent_admission_year())) {

			return true;
		}

		return false;
	}

	private void handleMultipartRequests(HttpServletRequest request, HttpServletResponse response) {

		List<FileItem> fileItems = new ArrayList<>();

		DiskFileItemFactory factory = new DiskFileItemFactory();

		// maximum size that will be stored in memory
		factory.setSizeThreshold(Constants.MAX_FILE_SIZE);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File(Constants.FILE_PATH_TEMP));
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(Constants.MAX_FILE_SIZE);

		try {
			// Parse the request to get file items.
			fileItems = upload.parseRequest(request);
			String operation = null;

			for (FileItem fi : fileItems) {
				if (fi.isFormField()) {
					if (!SmValidator.isStringEmpty(fi.getFieldName())
							&& fi.getFieldName().equals(Constants.LBL_OPERATION)
							&& fi.getString().matches(Constants.REGEX_FOR_DIGIT)) {

						//operation = Integer.parseInt(fi.getString());
						operation = fi.getString();
						break;
					}
				}
			}

			if (!isValidSession(request, operation)) {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			switch (operation) {
			case CaseConstants.INSERT_STUDENT_DETAILS:
				System.out.println("insert institute details in multipart request....");
				StudentDetails studentDetails = new StudentDetails();
				// InstituteRegistrationDetails instituteRegistrationDetails =
				// new InstituteRegistrationDetails();
				List<FileItem> collectedFileItems = new ArrayList<>();

				try {
					for (FileItem fi : fileItems) {
						if (fi.isFormField()) {
							String fieldName = fi.getFieldName();
							String fieldValue = fi.getString();
							System.out.println("Filed Name: " + fi.getFieldName() + ",\t Value: " + fi.getString());

							if (SmValidator.isStringEmpty(fieldName) || SmValidator.isStringEmpty(fieldValue)
									|| fieldName.equals(Constants.LBL_OPERATION))
								continue;

							/*
							 * if(String.valueOf(fieldName.charAt(fieldName.
							 * length()-1)).matches(Constants.REGEX_FOR_DIGIT))
							 * { //
							 * EduService.addStudentProfileField(floorPlans,
							 * fieldName, fieldValue); } else {
							 */
							EduService.setClassFieldValue(studentDetails, fieldName, fieldValue);
							/* } */
						} else {
							collectedFileItems.add(fi);
						}
					}
					UserDetails details = getUserFromSession(request);

					int user_id = details.getUserId();
					studentDetails.setUser_id(user_id);
					EduService.addStudentDetails(user_id, studentDetails);
					/*
					 * int userId = details.getUserId();
					 * instituteRegistrationDetails.setUserId(userId);
					 * EduService.addInstituteDetails(
					 * instituteRegistrationDetails);
					 */
					EduService.uploadStudentProfileImage(details, collectedFileItems);
					// PropertyService.uploadProeprtyImages(property,
					// collectedFileItems);
				} catch (Exception e) {
					System.out.println("Error while adding details");
					e.printStackTrace();
					request.setAttribute("message", "Couldn't details");
				}

				request.getRequestDispatcher("/Student_Index.jsp").forward(request, response);
				// TODO: request.getRequestDispatcher("/C?" + + "=" +
				// CaseConstants.ADD_PROEPRT_FORM).forward(request, response);
				break;

			case CaseConstants.INSERT_INSTITUTE_PROFILE_DETAILS:
				RegisteredInstituteDetails registeredInstituteDetails = new RegisteredInstituteDetails();
				collectedFileItems = new ArrayList<>();

				try {
					for (FileItem fi : fileItems) {
						if (fi.isFormField()) {
							String fieldName = fi.getFieldName();
							String fieldValue = fi.getString();
							System.out.println("Filed Name: " + fi.getFieldName() + ",\t Value: " + fi.getString());

							if (SmValidator.isStringEmpty(fieldName) || SmValidator.isStringEmpty(fieldValue)
									|| fieldName.equals(Constants.LBL_OPERATION))
								continue;

							/*
							 * if(String.valueOf(fieldName.charAt(fieldName.
							 * length()-1)).matches(Constants.REGEX_FOR_DIGIT))
							 * { //
							 * EduService.addStudentProfileField(floorPlans,
							 * fieldName, fieldValue); } else {
							 */
							EduService.setClassFieldValue(registeredInstituteDetails, fieldName, fieldValue);
							/* } */
						} else {
							collectedFileItems.add(fi);
						}
					}

					/*
					 * EduService.addStudentDetails(user_id,studentDetails);
					 */
					UserDetails details = getUserFromSession(request);
					int user_id = details.getUserId();
					EduInstituteService eduInstituteService = new EduInstituteService();
					InstituteRegistrationDetails instituteRegistrationDetails = eduInstituteService
							.getInstituteRegistrationDetailsByUserId(user_id);
					int instituteId = instituteRegistrationDetails.getInstitueRegID();
					registeredInstituteDetails.setInstitute_id(instituteId);
					EduService.addRegisteredInstituteProfileDetails(registeredInstituteDetails);
					/*
					 * int userId = details.getUserId();
					 * instituteRegistrationDetails.setUserId(userId);
					 * EduService.addInstituteDetails(
					 * instituteRegistrationDetails);
					 */
					System.out.println("instituteId is :- " + instituteId);
					registeredInstituteDetails = eduInstituteService
							.getRegisteredInstituteDetailsByInstituteId(instituteId);
					EduService.uploadInstituteProfileImages(registeredInstituteDetails, collectedFileItems);
					// PropertyService.uploadProeprtyImages(property,
					// collectedFileItems);
				} catch (Exception e) {
					System.out.println("Error while adding details");
					e.printStackTrace();
					request.setAttribute("message", "Couldn't details");
				}
				request.getRequestDispatcher("/Institute_Index.jsp").forward(request, response);
				break;

			case CaseConstants.INSERT_INSTITUTE_PROFILE_PIC:
				System.out.println("INSERT_INSTITUTE_PROFILE_PIC CASE");
				/*
				 * int institueRegID =
				 * Integer.parseInt(request.getParameter("institueRegID"));
				 * System.out.println("institueRegID is:-" + institueRegID);
				 */
				InstituteRegistrationDetails instituteRegistrationDetails = new InstituteRegistrationDetails();

				collectedFileItems = new ArrayList<>();

				try {
					for (FileItem fi : fileItems) {
						if (fi.isFormField()) {
							String fieldName = fi.getFieldName();
							String fieldValue = fi.getString();
							System.out.println("Filed Name: " + fi.getFieldName() + ",\t Value: " + fi.getString());

							if (SmValidator.isStringEmpty(fieldName) || SmValidator.isStringEmpty(fieldValue)
									|| fieldName.equals(Constants.LBL_OPERATION))
								continue;

							/*
							 * if(String.valueOf(fieldName.charAt(fieldName.
							 * length()-1)).matches(Constants.REGEX_FOR_DIGIT))
							 * { //
							 * EduService.addStudentProfileField(floorPlans,
							 * fieldName, fieldValue); } else {
							 */
							EduService.setClassFieldValue(instituteRegistrationDetails, fieldName, fieldValue);
							/* } */
						} else {
							collectedFileItems.add(fi);
						}
					}

					/*
					 * EduService.addStudentDetails(user_id,studentDetails);
					 */
					UserDetails details = getUserFromSession(request);
					int user_id = details.getUserId();
					EduInstituteService eduInstituteService = new EduInstituteService();
					instituteRegistrationDetails = eduInstituteService.getInstituteRegistrationDetailsByUserId(user_id);
					int instituteId = instituteRegistrationDetails.getInstitueRegID();
					instituteRegistrationDetails.setInstitueRegID(instituteId);
					/*
					 * EduService.updateInstituteProfilePicDetails(
					 * instituteRegistrationDetails);
					 */
					/*
					 * int userId = details.getUserId();
					 * instituteRegistrationDetails.setUserId(userId);
					 * EduService.addInstituteDetails(
					 * instituteRegistrationDetails);
					 */
					System.out.println("instituteId is :- " + instituteId);
					instituteRegistrationDetails = eduInstituteService
							.getInstituteRegistrationDetailsByInstituteId(instituteId);
					EduService.uploadInstituteProfilePic(instituteRegistrationDetails, collectedFileItems);
					// PropertyService.uploadProeprtyImages(property,
					// collectedFileItems);
				} catch (Exception e) {
					System.out.println("Error while adding details");
					e.printStackTrace();
					request.setAttribute("message", "Couldn't details");
				}
				request.getRequestDispatcher("/Institute_Index.jsp").forward(request, response);

				break;

			case CaseConstants.INSERT_COUNSELLOR_PROFILE_DETAILS:
				System.out.println("This is Counnsellor Profile");
				CounsellorProfileDetails counsellorProfileDetails = new CounsellorProfileDetails();

				collectedFileItems = new ArrayList<>();

				try {
					for (FileItem fi : fileItems) {
						if (fi.isFormField()) {
							String fieldName = fi.getFieldName();
							String fieldValue = fi.getString();
							System.out.println("Filed Name: " + fi.getFieldName() + ",\t Value: " + fi.getString());

							if (SmValidator.isStringEmpty(fieldName) || SmValidator.isStringEmpty(fieldValue)
									|| fieldName.equals(Constants.LBL_OPERATION))
								continue;

							/*
							 * if(String.valueOf(fieldName.charAt(fieldName.
							 * length()-1)).matches(Constants.REGEX_FOR_DIGIT))
							 * { //
							 * EduService.addStudentProfileField(floorPlans,
							 * fieldName, fieldValue); } else {
							 */
							EduService.setClassFieldValue(counsellorProfileDetails, fieldName, fieldValue);
							/* } */
						} else {
							collectedFileItems.add(fi);
						}
					}

					UserDetails details = getUserFromSession(request);
					int user_id = details.getUserId();
					counsellorProfileDetails.setUser_id(user_id);
					EduService.addCounsellorProfileDetails(counsellorProfileDetails);
					EduService.uploadCounsellorImageDetails(counsellorProfileDetails, collectedFileItems);
					// PropertyService.uploadProeprtyImages(property,
					// collectedFileItems);
				} catch (Exception e) {
					System.out.println("Error while adding details");
					e.printStackTrace();
					request.setAttribute("message", "Couldn't details");
				}
				request.getRequestDispatcher("/Counselor-index.jsp").forward(request, response);
				/*
				 * CounsellorProfileDetails counsellorProfiledetails =
				 * prepareBean.beanForInsertCounsellorProfileDetails(request);
				 * userDetails = getUserFromSession(request); userId =
				 * userDetails.getUserId();
				 * counsellorProfiledetails.setUserId(userId); updatedRows =
				 * eduCounsellorService.insertCounsellorProfileDetails(
				 * counsellorProfiledetails); System.out.println(
				 * "Counsellor Profile updated rows are:" + updatedRows);
				 * request.setAttribute("counsellorProfiledetails",
				 * counsellorProfiledetails);
				 * request.getRequestDispatcher("/CounselorProfile.jsp").forward
				 * (request, response);
				 */
				break;

			default:
				System.out.println("Invalid operation");
				response.sendRedirect("/index.jsp");
				break;
			}
		} catch (Exception ex) {
			System.out.println("Error while parsing multipart request" + ex);
		}

	}

	private String getOperationNumber(HttpServletRequest request) {
		if (!SmValidator.isStringEmpty(request.getParameter(Constants.LBL_OPERATION)))
			return request.getParameter(Constants.LBL_OPERATION);

		if (!SmValidator.isStringEmpty(request.getParameter(Constants.LBL_VERIFICATION_PARAMETER)))
			return String.valueOf(CaseConstants.EMAIL_VERIFICATION);

		return null;
	}
}