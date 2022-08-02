package com.firstapp.login;

import android.content.ContentValues;
	import android.content.Context;
	import android.database.Cursor;
	import android.database.sqlite.SQLiteDatabase;
	import android.database.sqlite.SQLiteOpenHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
		SQLiteDatabase db;

		private static final String DATABASE_NAME="database.db";
	    private static final int DATABASE_VERSION=1;
	    //-------------------------TABLE STUDENT------------------------------//
	    private static final String TABLE_STUDENT="_student";
		private static final String KEY_ID="id";
		private static final String KEY_NAME="name";
		private static final String KEY_EMAIL="email";
		private static final String KEY_PASS="pwd";
		//-------------------------TABLE STUDENT------------------------------//

		//-------------------------TABLE GRADE------------------------------//
		//private static final String TABLE_GRADE="_grade";
		private static final String G_ID="gradeId";
		private static final String G_GRADE="gradeName";
		private static final String G_POINT="gradePoint";
		//-------------------------TABLE GRADE------------------------------//

		//-------------------------TABLE COURSE------------------------------//
		private static final String C_ID="courseId";
		private static final String C_CODE="courseCode";
		private static final String C_NAME="courseName";
		private static final String C_CREDIT="courseCredit";
		//-------------------------TABLE COURSE------------------------------//

		//-------------------------TABLE REGISTERED COURSE------------------------------//
		private static final String RC_ID="rcID"; //registered course ID
		private static final String RC_SID="keyID";  //student ID
		private static final String RC_SEM="rcSem";  //semester
		private static final String RC_CODEID="courseId";  // course code ID
		private static final String RC_GRADEID="gradeId";  //grade ID

	    public DbHelper(Context context) {
               super(context, DATABASE_NAME, null,DATABASE_VERSION);
	    }

    	@Override
	    public void onCreate(SQLiteDatabase db){
        	        String student=" CREATE TABLE " +TABLE_STUDENT+ "("
        	                +KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
        	                +KEY_NAME+ " TEXT, " +KEY_EMAIL+ " TEXT, " +KEY_PASS+ " TEXT);";
			db.execSQL(student);
			db.execSQL("create table grade(gradeId integer primary key autoincrement, gradeName text, gradePoint text);");
			db.execSQL("create table course(courseId integer primary key autoincrement, courseCode text, courseName text, courseCredit text);");
			db.execSQL("create table registeredCourse" +
					   "(rcID integer primary key autoincrement, keyID text, rcSem text, courseId text, gradeId text, " +
					   "foreign key (keyID) REFERENCES parent(KEY_ID), foreign key (courseId) REFERENCES parent(courseId), foreign key (gradeId) REFERENCES parent(gradeId));");
		}

        @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        	        db.execSQL("DROP TABLE IF EXISTS "+TABLE_STUDENT);
        	        db.execSQL("DROP TABLE IF EXISTS grade");
        	        db.execSQL("DROP TABLE IF EXISTS course");
        	        db.execSQL("DROP TABLE IF EXISTS registeredCourse");
        	        onCreate(db);
        	    }


	public Boolean insertStudent(String name, String email, String pwd) { //insert value to student table
		db=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(KEY_NAME,name);
		values.put(KEY_EMAIL,email);
		values.put(KEY_PASS,pwd);
		long result = db.insert(TABLE_STUDENT, null, values);
		if (result == -1)
			return false;
		else
			return true;
	}

    //check username
    public Boolean checkusername(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_STUDENT+" where id=?", new String[] {id});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    //check username and password
    public Boolean checkusernamepassword(String email, String pwd){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_STUDENT+" where " +KEY_EMAIL+ "=? and " +KEY_PASS+ "=?", new String[] {email,pwd});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }


		public Boolean insertGrade(String gradeName, String gradePoint) { //insert value to grade table
			db = this.getWritableDatabase();
			ContentValues val = new ContentValues(); // i changed the value name to val

			val.put("gradeName", gradeName);
			val.put("gradePoint", gradePoint);
			long result = db.insert("grade", null, val);
			if (result == -1)
				return false;
			else
				return true;
		}

		//insert data to course table
		public Boolean insertCourse(String courseCode, String courseName, String courseCredit) { //insert value to grade table
			db = this.getWritableDatabase();
			ContentValues valu = new ContentValues(); // i changed the value name to val

			valu.put("courseCode", courseCode);
			valu.put("courseName", courseName);
			valu.put("courseCredit", courseCredit);
			long result = db.insert("course", null, valu);
			if (result == -1)
				return false;
			else
				return true;
		}

    public long insertRegisteredCourse(String keyID, String rcSem, String courseId, String gradeId) { //insert value to student table
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("keyID",1);
        values.put("rcSem",rcSem);
        values.put("courseId",courseId);
        values.put("gradeId",gradeId);
        return db.insert("registeredCourse",null,values);
    }


   	    public String getData() { //view all student data
	    	db=this.getReadableDatabase();
       	    String[] columns=new String[] {KEY_ID,KEY_NAME,KEY_EMAIL,KEY_PASS};
			String result;
			try (Cursor cursor = db.query(TABLE_STUDENT, columns, null, null, null, null, null)) {
				int iId = cursor.getColumnIndex(KEY_ID);
				int iName = cursor.getColumnIndex(KEY_NAME);
				int iEmail = cursor.getColumnIndex(KEY_EMAIL);
				int iPass = cursor.getColumnIndex(KEY_PASS);
				result = "";
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					result = result +
							"Id: " + cursor.getString(iId) + "\n" +
							"Name: " + cursor.getString(iName) + "\n" +
							"Email: " + cursor.getString(iEmail) + "\n" +
							"Password: " + cursor.getString(iPass) + "\n\n";
				}
			}
			db.close();
       	        return result;
       	    }

	public String getStudent() { //view student name
		db=this.getReadableDatabase();
		String[] columns=new String[] {KEY_ID,KEY_NAME,KEY_EMAIL,KEY_PASS};
		String result;
		try (Cursor cursor = db.query(TABLE_STUDENT, columns, null, null, null, null, null)) {
			int iName = cursor.getColumnIndex(KEY_NAME);
			result = "";
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				result = result +"Welcome "+ cursor.getString(iName) + "\n";
				break;
			}
		}
		db.close();
		return result;
	}


       	    public String getGradeData() { //view all grade data
       	        db=this.getReadableDatabase();
       	        String[] columns=new String[] {G_ID,G_GRADE,G_POINT};
				String r;
       	       try(Cursor cursor = db.query("GRADE",columns,null,null,null,null,null)) {
				   int gId = cursor.getColumnIndex(G_ID);
				   int gGrade = cursor.getColumnIndex(G_GRADE);
				   int gPoint = cursor.getColumnIndex(G_POINT);
				   r = "";
				   for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					   r = r +
							   "\n"+cursor.getString(gId)+"\t\t" +cursor.getString(gPoint)+"\t\t" +
							   cursor.getString(gGrade);
				   }
			   }
       	        db.close();
       	        return r;
       	    }
//get course data
		public String getCourseData() { //view all grade data
			db=this.getReadableDatabase();
			String[] columns=new String[] {C_ID,C_CODE,C_NAME,C_CREDIT};
			String r;
			try(Cursor cursor = db.query("COURSE",columns,null,null,null,null,null)) {
				int c_id = cursor.getColumnIndex(C_ID);
				int c_code = cursor.getColumnIndex(C_CODE);
				int c_name = cursor.getColumnIndex(C_NAME);
				int c_credit = cursor.getColumnIndex(C_CREDIT);
				r = "";
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					r = r +"\n"+cursor.getString(c_id)+"\t" +cursor.getString(c_code)+"\t\t" + cursor.getDouble(c_credit)+"\t\t" +
							cursor.getString(c_name);
				}
			}
			db.close();
			return r;
		}

		public String calcCPA(){
			db=this.getReadableDatabase();
			DecimalFormat df = new DecimalFormat("#.##");
			String r,r2,r3,r4,r5,name,id;
			int i=0,i2=0,i3=0,i4=0,i5=0;
			double sem1 = 0, sem2 = 0, sem3 = 0, sem4 = 0, sem5 = 0, gpa=0.0,cgpa=0.0,credit=0.0;
			try(Cursor cursor =  db.rawQuery("SELECT R.rcID, S.id, S.name, G.GRADEID, G.GRADENAME, G.GRADEPOINT," +
					"C.COURSEID,C.COURSECODE,C.COURSENAME,C.COURSECREDIT," +
					"R.KEYID,R.RCSEM,R.COURSEID FROM REGISTEREDCOURSE R" +
					" JOIN COURSE C ON R.COURSEID = C.COURSEID" +
					" JOIN _student S ON R.keyID = S.id" +
					" JOIN GRADE G ON R.GRADEID = G.GRADEID" +
					" ORDER BY R.RCID",null)) {
				int rc_sid = cursor.getColumnIndex(RC_SID);
				int iName = cursor.getColumnIndex(KEY_NAME);
				int rc_sem = cursor.getColumnIndex(RC_SEM);
				int c_code = cursor.getColumnIndex(C_CODE);
				int c_name = cursor.getColumnIndex(C_NAME);
				int c_credit = cursor.getColumnIndex(C_CREDIT);
				int gGrade = cursor.getColumnIndex(G_GRADE);
				int gPoint = cursor.getColumnIndex(G_POINT);
				r = "";r2="";r3="";r4="";r5="";;name="";id="";
				String grade = "";
                					for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
						if(cursor.getInt(rc_sem) == 1) {
						r = r +	"\n"+cursor.getString(c_code)+"\t\t" + cursor.getDouble(c_credit)+"\t\t" +
								cursor.getString(c_name)+"\t\t" +  cursor.getString(gGrade) ;
								sem1+=cursor.getDouble(c_credit)* cursor.getDouble(gPoint);
								i+=cursor.getDouble(c_credit);
							}

					}


				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					if(cursor.getInt(rc_sem) == 2) {
						r2 = r2 +"\n"+cursor.getString(c_code)+"\t\t" + cursor.getDouble(c_credit)+"\t\t" +
								cursor.getString(c_name)+"\t\t" +  cursor.getString(gGrade) ;
								sem2+=cursor.getDouble(c_credit)* cursor.getDouble(gPoint);
								i2+=cursor.getDouble(c_credit);
					}
				}

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					if(cursor.getInt(rc_sem) == 3) {
						r3 = r3 +"\n"+cursor.getString(c_code)+"\t\t" + cursor.getDouble(c_credit)+"\t\t" +
								cursor.getString(c_name)+"\t\t" +  cursor.getString(gGrade) ;
						sem3+=cursor.getDouble(c_credit)* cursor.getDouble(gPoint);
						i3+=cursor.getDouble(c_credit);
					}
				}

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					if(cursor.getInt(rc_sem) == 4) {
						r4 = r4 +"\n"+cursor.getString(c_code)+"\t\t" + cursor.getDouble(c_credit)+"\t\t" +
								cursor.getString(c_name)+"\t\t" +  cursor.getString(gGrade) ;
						sem4+=cursor.getDouble(c_credit)* cursor.getDouble(gPoint);
						i4+=cursor.getDouble(c_credit);
					}
				}
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					if(cursor.getInt(rc_sem) == 5) {
						r5 = r5 +"\n"+cursor.getString(c_code)+"\t\t" + cursor.getDouble(c_credit)+"\t\t" +
								cursor.getString(c_name)+"\t\t" +  cursor.getString(gGrade) ;
						sem5+=cursor.getDouble(c_credit)* cursor.getDouble(gPoint);
						i5+=cursor.getDouble(c_credit);
					}
				}

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					name = name + cursor.getString(iName) + "\n";
					break;
				}

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					id = id + cursor.getString(rc_sid) + "\n";
					break;
				}

				gpa = sem1+sem2+sem3+sem4+sem5;
				credit = i+i2+i3+i4+i5;
				cgpa = gpa/credit;
				//gpa sem1-5
				double gpa1=0.0,gpa2=0.0,gpa3=0.0,gpa4=0.0,gpa5=0.0;
				gpa1=sem1/i;gpa2=sem2/i2;gpa3=sem3/i3;gpa4=sem4/i5;gpa5=sem5/i5;
				String result="",result2="",result3="",result4="",result5="",cgpaResult="";
				/*------DETERMINE DEAN LIST FOR GPA-----*/
				if(gpa1>=3.5)//SEM1
					result = "DEAN LIST (AD)";
				else if(gpa1 >=3.00 && gpa1 <=3.49)
					result = "PASS";
				else
					result ="FAIL";

				if(gpa2>=3.5)//SEM2
					result2 = "DEAN LIST (AD)";
				else if(gpa2 >=3.00 && gpa2 <=3.49)
					result2 = "PASS";
				else
					result2 ="FAIL";

				if(gpa3>=3.5)//SEM 3
					result3 = "DEAN LIST (AD)";
				else if(gpa3 >=3.00 && gpa3 <=3.49)
					result3 = "PASS";
				else
					result3 ="FAIL";

				if(gpa4>=3.5)//SEM 3
					result4 = "DEAN LIST (AD)";
				else if(gpa4 >=3.00 && gpa4 <=3.49)
					result4 = "PASS";
				else
					result4 ="FAIL";

				if(gpa5>=3.5)//SEM 3
					result5 = "DEAN LIST (AD)";
				else if(gpa5 >=3.00 && gpa5 <=3.49)
					result5 = "PASS";
				else
					result5 ="FAIL";


				/*------DETERMINE DEAN LIST FOR CGPA (ALL SEMESTERS)-----*/
				if(cgpa>=3.5)
					cgpaResult = "DEAN LIST (AD)";
				else if(cgpa >=3.00 && cgpa <=3.49)
					cgpaResult = "PASS";
				else
					cgpaResult ="FAIL";

				String semester1  = "SEMESTER 1: "+df.format(gpa1)+" ("+result+") " + r;
				String semester2  = "\n\nSEMESTER 2: "+df.format(gpa2)+" ("+result2+") " + r2;
				String semester3  = "\n\nSEMESTER 3: "+df.format(gpa3)+" ("+result3+") " + r3;
				String semester4  = "\n\nSEMESTER 4: "+df.format(gpa4)+" ("+result4+") " + r4;
				String semester5  = "\n\nSEMESTER 5: "+df.format(gpa5)+" ("+result5+") " + r5;
				grade = "STUDENT NAME  : " +name+
						"STUDENT ID          :"+id+"\n\n"+semester1+semester2+semester3+semester4+semester5+

				"\n\nCGPA: "+df.format(cgpa)+" ("+cgpaResult+")";

				db.close();
				return grade;
			}
		}

		public String getRegisteredCourse() {
			db=this.getReadableDatabase();
			String r,r2,r3,r4,r5;
			try(Cursor cursor =  db.rawQuery("SELECT R.rcID, S.id, S.name, G.GRADEID, G.GRADENAME, G.GRADEPOINT," +
							"C.COURSEID,C.COURSECODE,C.COURSENAME,C.COURSECREDIT," +
							"R.KEYID,R.RCSEM,R.COURSEID FROM REGISTEREDCOURSE R" +
							" JOIN COURSE C ON R.COURSEID = C.COURSEID" +
							" JOIN _student S ON R.keyID = S.id" +
							" JOIN GRADE G ON R.GRADEID = G.GRADEID" +
							" ORDER BY R.RCID",null)) {
				int rc_id = cursor.getColumnIndex(RC_ID);
				int rc_sem = cursor.getColumnIndex(RC_SEM);
				int c_code = cursor.getColumnIndex(C_CODE);
				int c_name = cursor.getColumnIndex(C_NAME);
				int gGrade = cursor.getColumnIndex(G_GRADE);
				r = "";r2="";r3="";r4="";r5="";
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					if (cursor.getInt(rc_sem) == 1) {
						r = r +
								"\n" + cursor.getString(rc_id) + "\t" + cursor.getString(c_code)  +"\t" +
								cursor.getString(c_name) + "\t" + cursor.getString(gGrade);
					}

				}

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					if(cursor.getInt(rc_sem) == 2) {
						r2 = r2 +
								"\n" + cursor.getString(rc_id) + "\t" + cursor.getString(c_code)  +"\t" +
								cursor.getString(c_name) + "\t" + cursor.getString(gGrade);
					}
				}

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					if(cursor.getInt(rc_sem) == 3) {
						r3 = r3 +
								"\n" + cursor.getString(rc_id) + "\t" + cursor.getString(c_code) +"\t" +
								cursor.getString(c_name) + "\t" + cursor.getString(gGrade);
					}
				}

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					if(cursor.getInt(rc_sem) == 4) {
						r4 = r4 +
								"\n" + cursor.getString(rc_id) + "\t" + cursor.getString(c_code) +"\t" +
								cursor.getString(c_name) + "\t" + cursor.getString(gGrade);
					}
				}

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					if(cursor.getInt(rc_sem) == 5) {
						r5 = r5 +
								"\n" + cursor.getString(rc_id) + "\t" + cursor.getString(c_code) +"\t" +
								cursor.getString(c_name) + "\t" + cursor.getString(gGrade);
					}
				}

			}
			String res ="";
			res = "SEMESTER 1: "+ r+"\n\n\nSEMESTER 2: "+r2 + "\n\n\nSEMESTER 3: "+r3+ "\n\n\nSEMESTER 4: "+r4+ "\n\n\nSEMESTER 5: "+r5;
			db.close();
			return res;
		}
    /*--delete student--*/
       	    public void deleteStudent(long l) { // delete student value
       	        db=this.getWritableDatabase();
       	        db.delete(TABLE_STUDENT,KEY_ID+"="+l,null);
       	    }
    /*--delete grade--*/
       	    public void deleteGrade(long l) { // delete student value
       	        db=this.getWritableDatabase();
       	        db.delete("GRADE",G_ID+"="+l,null);
       	    }
    /*--delete course--*/
		public void deleteCourse(long l) { // delete student value
			db = this.getWritableDatabase();
			db.delete("course", C_ID + "=" + l, null);
		}
    /*--delete registeredCourse--*/
		public void deleteRCourse(long l) { // delete registered course value
			db=this.getWritableDatabase();
			db.delete("registeredCourse",RC_ID+"="+l,null);
		}
    /*--update Student--*/
    public void updateStudent(long l, String name, String email, String pwd) {
       	        db=this.getWritableDatabase();
       	        ContentValues values=new ContentValues();
       	        values.put(KEY_NAME,name);
       	        values.put(KEY_EMAIL,email);
       	        values.put(KEY_PASS,pwd);
       	        db.update(TABLE_STUDENT,values,KEY_ID+"="+l,null);
       	        db.close();
       	    }
         /*--update Grade--*/
       	    public void updateGrade(long l, String gradeName, String gradePoint) {
       	        db=this.getWritableDatabase();
       	        ContentValues values=new ContentValues();
       	        values.put(G_GRADE,gradeName);
       	        values.put(G_POINT,gradePoint);
       	        db.update("GRADE",values,G_ID+"="+l,null);
       	        db.close();
       	    }
         /*--update Course--*/
		public void updateCourse(long l, String courseCode, String courseName, String courseCredit) {
			db=this.getWritableDatabase();
			ContentValues values=new ContentValues();
			values.put(C_CODE,courseCode);
			values.put(C_NAME,courseName);
			values.put(C_CREDIT,courseCredit);
			db.update("COURSE",values,C_ID+"="+l,null);
			db.close();
		}
         /*--update registeredCourse--*/
		public void updateRCourse(long l, String keyID, String rcSem, String courseId, String gradeId) {
			db=this.getWritableDatabase();
			ContentValues values=new ContentValues();
			values.put(RC_SID,keyID);
			values.put(RC_SEM,rcSem);
			values.put(RC_CODEID,courseId);
			values.put(RC_GRADEID,gradeId);
			db.update("registeredCourse",values,RC_ID+"="+l,null);
			db.close();
		}

		public String getName(long l1) {
       	        db=this.getReadableDatabase();
       	        String[] columns=new String[]{KEY_ID,KEY_NAME,KEY_EMAIL,KEY_PASS};
       	        Cursor cursor=db.query(TABLE_STUDENT,columns,KEY_ID+"="+l1,null,null,null,null);
       	        if(cursor!=null){
           	            cursor.moveToFirst();
           	            String name=cursor.getString(1);
           	            return name;
           	        }
       	        return null;
       	    }
		   public String getEmail(long l1) {
       	        db=this.getReadableDatabase();
       	        String[] columns=new String[]{KEY_ID,KEY_NAME,KEY_EMAIL,KEY_PASS};
       	        Cursor cursor=db.query(TABLE_STUDENT,columns,KEY_ID+"="+l1,null,null,null,null);
       	        if(cursor!=null){
           	            cursor.moveToFirst();
           	            String name=cursor.getString(2);
           	            return name;
           	        }
       	        return null;
       	    }
        	    public String getMobile(long l1) {
       	        db=this.getReadableDatabase();
       	        String[] columns=new String[]{KEY_ID,KEY_NAME,KEY_EMAIL,KEY_PASS};
       	        Cursor cursor=db.query(TABLE_STUDENT,columns,KEY_ID+"="+l1,null,null,null,null);
       	        if(cursor!=null){
           	            cursor.moveToFirst();
           	            String name=cursor.getString(3);
           	            return name;
           	        }
       	        return null;
       	    }

			 //get grade data
       	    public String getGrade(long l1) {
       	        db=this.getReadableDatabase();
       	        String[] columns=new String[] {G_ID,G_GRADE,G_POINT};
       	        Cursor cursor=db.query("GRADE",columns,G_ID+"="+l1,null,null,null,null);
       	        if(cursor!=null){
           	            cursor.moveToFirst();
           	            String name=cursor.getString(1);
           	            return name;
           	        }
       	        return null;
       	    }

			   //get grade point data
        	    public String getPoint(long l1) {
       	        db=this.getReadableDatabase();
       	        String[] columns=new String[] {G_ID,G_GRADE,G_POINT};
       	        Cursor cursor=db.query("GRADE",columns,G_ID+"="+l1,null,null,null,null);
       	        if(cursor!=null){
           	            cursor.moveToFirst();
           	            String name=cursor.getString(2);
           	            return name;
           	        }
       	        return null;
       	    }

		//get courseCode data
		public String getCodeCourse(long l1) {
			db=this.getReadableDatabase();
			String[] columns=new String[] {C_ID,C_CODE,C_NAME,C_CREDIT};
			Cursor cursor=db.query("COURSE",columns,C_ID+"="+l1,null,null,null,null);
			if(cursor!=null){
				cursor.moveToFirst();
				String name=cursor.getString(1);
				return name;
			}
			return null;
		}
			   /*--Get CourseCode Name--*/
        	    public String getCodeName(long l1) {
       	        db=this.getReadableDatabase();
					String[] columns=new String[] {C_ID,C_CODE,C_NAME,C_CREDIT};

					Cursor cursor=db.query("COURSE",columns,C_ID+"="+l1,null,null,null,null);
       	        if(cursor!=null){
           	            cursor.moveToFirst();
           	            String name=cursor.getString(2);
           	            return name;
           	        }
       	        return null;
       	    }

			   //get COURSE CREDIT data
        	    public String getCodeCredit(long l1) {
       	        db=this.getReadableDatabase();
					String[] columns=new String[] {C_ID,C_CODE,C_NAME,C_CREDIT};
       	        Cursor cursor=db.query("COURSE",columns,C_ID+"="+l1,null,null,null,null);
       	        if(cursor!=null){
           	            cursor.moveToFirst();
           	            String name=cursor.getString(3);
           	            return name;
           	        }
       	        return null;
       	    }

	public String getRcStudentID(long l1) { //GET STUDENT ID FROM RC
		db=this.getReadableDatabase();
		String[] columns=new String[] {RC_ID,RC_SID,RC_SEM,RC_CODEID,RC_GRADEID};

		Cursor cursor=db.query("registeredCourse",columns,RC_ID+"="+l1,null,null,null,null);
		if(cursor!=null){
			cursor.moveToFirst();
			String name=cursor.getString(1);
			return name;
		}
		return null;
	}
			public String getRcSem(long l1) { //GET semester FROM RC
		db=this.getReadableDatabase();
		String[] columns=new String[] {RC_ID,RC_SID,RC_SEM,RC_CODEID,RC_GRADEID};

		Cursor cursor=db.query("registeredCourse",columns,RC_ID+"="+l1,null,null,null,null);
		if(cursor!=null){
			cursor.moveToFirst();
			String name=cursor.getString(2);
			return name;
		}
		return null;
	}
			public String getRcCodeID(long l1) { //GET code ID FROM RC
		db=this.getReadableDatabase();
		String[] columns=new String[] {RC_ID,RC_SID,RC_SEM,RC_CODEID,RC_GRADEID};

		Cursor cursor=db.query("registeredCourse",columns,RC_ID+"="+l1,null,null,null,null);
		if(cursor!=null){
			cursor.moveToFirst();
			String name=cursor.getString(3);
			return name;
		}
		return null;
	}
			public String getRcGradeID(long l1) { //GET grade ID FROM RC
		db=this.getReadableDatabase();
		String[] columns=new String[] {RC_ID,RC_SID,RC_SEM,RC_CODEID,RC_GRADEID};

		Cursor cursor=db.query("registeredCourse",columns,RC_ID+"="+l1,null,null,null,null);
		if(cursor!=null){
			cursor.moveToFirst();
			String name=cursor.getString(4);
			return name;
		}
		return null;
	}

    /*--Retrieve GPA for all semester--*/
	public ArrayList<String> GPASEM1() {
		db=this.getReadableDatabase();
		ArrayList<String> yData = new ArrayList<String>();
		DecimalFormat df = new DecimalFormat("#.##");
		int i=0,i2=0,i3=0,i4=0,i5=0;
		double sem1 = 0, sem2 = 0, sem3 = 0, sem4 = 0, sem5 = 0, gpa=0.0,cgpa=0.0,credit=0.0;
		try(Cursor cursor =  db.rawQuery("SELECT R.rcID, S.id, S.name, G.GRADEID, G.GRADENAME, G.GRADEPOINT," +
				"C.COURSEID,C.COURSECODE,C.COURSENAME,C.COURSECREDIT," +
				"R.KEYID,R.RCSEM,R.COURSEID FROM REGISTEREDCOURSE R" +
				" JOIN COURSE C ON R.COURSEID = C.COURSEID" +
				" JOIN _student S ON R.keyID = S.id" +
				" JOIN GRADE G ON R.GRADEID = G.GRADEID" +
				" ORDER BY R.RCID",null)) {
			int rc_sem = cursor.getColumnIndex(RC_SEM);
			int c_credit = cursor.getColumnIndex(C_CREDIT);
			int gPoint = cursor.getColumnIndex(G_POINT);

			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				if(cursor.getInt(rc_sem) == 1) {
					sem1+=cursor.getDouble(c_credit)* cursor.getDouble(gPoint);
					i+=cursor.getDouble(c_credit);
				}
			}
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				if(cursor.getInt(rc_sem) == 2) {
					sem2+=cursor.getDouble(c_credit)* cursor.getDouble(gPoint);
					i2+=cursor.getDouble(c_credit);
				}
			}

			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				if(cursor.getInt(rc_sem) == 3) {
					sem3+=cursor.getDouble(c_credit)* cursor.getDouble(gPoint);
					i3+=cursor.getDouble(c_credit);
				}
			}
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				if(cursor.getInt(rc_sem) == 4) {
					sem4+=cursor.getDouble(c_credit)* cursor.getDouble(gPoint);
					i4+=cursor.getDouble(c_credit);
				}
			}
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				if(cursor.getInt(rc_sem) == 5) {
					sem5+=cursor.getDouble(c_credit)* cursor.getDouble(gPoint);
					i5+=cursor.getDouble(c_credit);
				}
			}
			//gpa sem1-5
			double gpa1,gpa2,gpa3,gpa4,gpa5;
			gpa1=sem1/i;gpa2=sem2/i2;gpa3=sem3/i3;gpa4=sem4/i4;gpa5=sem5/i5; //calculate gpa for each semester

 			yData.add(df.format(gpa));
 			yData.add(df.format(gpa1));
 			yData.add(df.format(gpa2));
 			yData.add(df.format(gpa3));
			yData.add(df.format(gpa4));
			yData.add(df.format(gpa5));
		}
		db.close();
		return yData;
	}
}
