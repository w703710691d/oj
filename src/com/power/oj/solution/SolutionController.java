package com.power.oj.solution;

import jodd.util.StringBand;
import jodd.util.StringUtil;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.power.oj.common.OjConstants;
import com.power.oj.common.OjController;
import com.power.oj.common.ResultType;
import com.power.oj.common.model.LanguageModel;
import com.power.oj.contest.ContestModel;
import com.power.oj.judge.Judge;
import com.power.oj.problem.ProblemModel;
import com.power.oj.user.LoginInterceptor;
import com.power.oj.user.UserModel;

public class SolutionController extends OjController
{

	@ActionKey("/status")
	public void index()
	{
		int pageNumber = getParaToInt("p", 1);
		int pageSize = getParaToInt("s", 20);
		int result = getParaToInt("result", -1);
		int language = getParaToInt("language", -1);
		int pid = 0;
		if (StringUtil.isNotBlank(getPara("pid")))
			pid = getParaToInt("pid", 0);
		String userName = getPara("name");
		StringBand query = new StringBand();

		if (result > -1)
		{
			query.append("&result=").append(result);
		}
		if (language > -1)
		{
			query.append("&language=").append(language);
		}
		if (pid > 0)
		{
			query.append("&pid=").append(pid);
		}
		if (StringUtil.isNotBlank(userName))
		{
			query.append("&name=").append(userName);
		}

		setAttr("pageTitle", "Status");
		setAttr("solutionList", SolutionModel.dao.getPage(pageNumber, pageSize, result, language, pid, userName));
		setAttr("program_languages", OjConstants.program_languages);
		setAttr("judge_result", OjConstants.judge_result);
		setAttr("result", result);
		setAttr("language", language);
		setAttr("pid", getPara("pid"));
		setAttr("name", getPara("name"));
		setAttr("query", query.toString());

		render("index.html");
	}

	@ActionKey("/code")
	@Before(LoginInterceptor.class)
	public void show()
	{
		int sid = getParaToInt(0);
		boolean isAdmin = getAttr("adminUser") != null;
		SolutionModel solutionModel = SolutionModel.dao.findFirst("SELECT * FROM solution WHERE sid=?", sid);
		ResultType resultType = (ResultType) OjConstants.result_type.get(solutionModel.getInt("result"));
		int uid = solutionModel.getInt("uid");
		int loginUid = getAttrForInt("userID");
		if (uid != loginUid && !isAdmin)
		{
			redirect("/status", "Permission Denied.", "error", "Error!");
			return;
		}

		if (!isAdmin)
		{
			String error = solutionModel.getStr("error");
			if (error != null)
				solutionModel.set("error", error.replaceAll(StringUtil.replace(OjConstants.get("work_path"), "\\",
						"\\\\"), ""));
		}
		
		String problemTitle = "";
		int cid = solutionModel.getInt("cid");
		System.out.println(cid);
		if(cid > 0)
		{
			int num = solutionModel.getInt("num");
			problemTitle = ContestModel.dao.getProblemTitle(cid, num);
			setAttr("alpha", (char)(num + 'A'));
			setAttr("cid", cid);
		}
		else
		{
			problemTitle = ProblemModel.dao.getProblemTitle(solutionModel.getInt("pid"));
		}
		
		setAttr("pageTitle", "Source code");
		setAttr("problemTitle", problemTitle);
		setAttr("user", UserModel.dao.findById(uid, "name").get("name"));
		LanguageModel language = (LanguageModel) OjConstants.language_type.get(solutionModel.getInt("language"));
		setAttr("language", language.get("name"));

		setAttr("resultLongName", resultType.getLongName());
		setAttr("resultName", resultType.getName());
		setAttr("solution", solutionModel);

		String brush = getAttrForStr("language").toLowerCase();
		if ("G++".equalsIgnoreCase(brush) || "GCC".equalsIgnoreCase(brush))
			brush = "cpp";
		setAttr("brush", brush);

		render("code.html");
	}

	public void add()
	{
		renderText("TODO");
	}

	@Before(LoginInterceptor.class)
	public void save()
	{
		SolutionModel solutionModel = getModel(SolutionModel.class, "solution");
		solutionModel.set("uid", getAttrForInt("userID"));
		String url = "/status";
		if(solutionModel.get("cid") != null)
		{
			int cid = solutionModel.getInt("cid");
			if(cid > 0)
				url = "/contest/status/"+cid;
		}
		
		if (solutionModel.addSolution())
		{
			ProblemModel problemModel = ProblemModel.dao.findById(solutionModel.getInt("pid"));
			if (problemModel == null)
			{
				redirect(url, "Please choose a correct problem.", "error", "Error!");
				return;
			}
			long stime = System.currentTimeMillis() / 1000;
			problemModel.set("submit", problemModel.getInt("submit") + 1).set("stime", stime);
			problemModel.update();

			synchronized (Judge.judgeList)
			{
				Judge.judgeList.add(solutionModel);
				if (Judge.threads < 1)
				{
					Judge.threads += 1;
					@SuppressWarnings("unused")
					Judge judge = new Judge();
				}
			}
			System.out.println(solutionModel.getInt("sid"));
		} else
			redirect(url, "Submit failed, maybe code length is incorrect.", "error", "Error!");

		redirect(url);
	}

	public void edit()
	{
		renderText("TODO");
	}
}