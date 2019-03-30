package com.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao.ArticleDAO;
import com.dao.CartDAO;
import com.dao.CateDAO;
import com.dao.DetailsDAO;
import com.dao.GoodsDAO;
import com.dao.OrdersDAO;
import com.dao.UsersDAO;
import com.entity.Article;
import com.entity.Cart;
import com.entity.Cate;
import com.entity.Details;
import com.entity.Goods;
import com.entity.Orders;
import com.entity.Users;
import com.util.VeDate;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/index")
public class IndexAction extends BaseAction {

	private UsersDAO usersDAO;
	private CateDAO cateDAO;
	private CartDAO cartDAO;
	private OrdersDAO ordersDAO;
	private ArticleDAO articleDAO;
	private DetailsDAO detailsDAO;
	private GoodsDAO goodsDAO;

	// 公共方法 提供公共查询数据
	private void front(Map<String, Object> map) {
		getRequest().setAttribute("title", "江中网上便利店");
		List<Cate> cateList = this.cateDAO.getAllCate();
		map.put("cateList", cateList);
		List<Goods> hotList = this.goodsDAO.getGoodsByHot();
		map.put("hotList", hotList);
	}

	// 首页显示的控制器
	@RequestMapping("index.action")
	public String index(Map<String, Object> map) {
		this.front(map);
		List<Cate> cateList = this.cateDAO.getAllCate();
		List<Cate> frontList = new ArrayList<Cate>();
		for (Cate cate : cateList) {
			List<Goods> goodsList = this.goodsDAO.getGoodsByCate(cate.getCateid());
			cate.setGoodsList(goodsList);
			frontList.add(cate);
		}
		map.put("frontList", frontList);
		List<Goods> newsList = this.goodsDAO.getGoodsByNews();
		map.put("newsList", newsList);
		List<Article> articleList = this.articleDAO.getFrontArticle();
		map.put("articleList", articleList);
		return "users/index";
	}

	// 按分类查询
	@RequestMapping("cate.action")
	public String cate(Map<String, Object> map, String id) {
		this.front(map);
		Goods goods = new Goods();
		goods.setCateid(id);
		goods.setStatus("上架");
		List<Goods> goodsList = this.goodsDAO.getGoodsByCond(goods);
		map.put("goodsList", goodsList);
		return "users/list";
	}

	// 推荐零食
	@RequestMapping("recommend.action")
	public String recommend(Map<String, Object> map, String id) {
		this.front(map);
		Goods goods = new Goods();
		goods.setRecommend("是");
		goods.setStatus("上架");
		List<Goods> goodsList = this.goodsDAO.getGoodsByCond(goods);
		map.put("goodsList", goodsList);
		return "users/list";
	}

	// 特价零食
	@RequestMapping("special.action")
	public String special(Map<String, Object> map, String id) {
		this.front(map);
		Goods goods = new Goods();
		goods.setSpecial("是");
		goods.setStatus("上架");
		List<Goods> goodsList = this.goodsDAO.getGoodsByCond(goods);
		map.put("goodsList", goodsList);
		return "users/list";
	}

	// 全部零食
	@RequestMapping("all.action")
	public String all(Map<String, Object> map, String number) {
		this.front(map);
		Goods goods = new Goods();
		goods.setStatus("上架");
		List<Goods> goodsList = this.goodsDAO.getGoodsByCond(goods);
		map.put("goodsList", goodsList);
		return "users/list";
	}

	// 新品上架
	@RequestMapping("news.action")
	public String news(Map<String, Object> map, String number) {
		this.front(map);
		List<Goods> goodsList = this.goodsDAO.getGoodsByNews();
		map.put("goodsList", goodsList);
		return "users/list";
	}

	// 查询零食
	@RequestMapping("query.action")
	public String query(Map<String, Object> map, String name) {
		this.front(map);
		Goods goods = new Goods();
		goods.setGoodsname(name);
		List<Goods> goodsList = this.goodsDAO.getGoodsByLike(goods);
		map.put("goodsList", goodsList);
		return "users/list";
	}

	// 零食详情
	@RequestMapping("detail.action")
	public String detail(Map<String, Object> map, String id) {
		this.front(map);
		Goods goods = this.goodsDAO.getGoodsById(id);
		goods.setHits("" + (Integer.parseInt(goods.getHits()) + 1));
		this.goodsDAO.updateGoods(goods);
		map.put("goods", goods);
		return "users/detail";
	}

	// 商城公告
	@RequestMapping("article.action")
	public String article(Map<String, Object> map) {
		this.front(map);
		List<Article> articleList = this.articleDAO.getAllArticle();
		map.put("articleList", articleList);
		return "users/article";
	}

	@RequestMapping("read.action")
	public String read(Map<String, Object> map, String id) {
		this.front(map);
		Article article = this.articleDAO.getArticleById(id);
		article.setHits("" + (Integer.parseInt(article.getHits()) + 1));
		this.articleDAO.updateArticle(article);
		map.put("article", article);
		return "users/read";
	}

	// 添加零食到购物车
	@RequestMapping("addcart.action")
	public String addcart(Map<String, Object> map) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) getRequest().getSession().getAttribute("userid");
		Cart cart = new Cart();
		cart.setCartid(VeDate.getStringDatex());
		cart.setAddtime(VeDate.getStringDateShort());
		cart.setGoodsid(getRequest().getParameter("goodsid"));
		cart.setNum(getRequest().getParameter("num"));
		cart.setPrice(getRequest().getParameter("price"));
		cart.setUsersid(userid);
		this.cartDAO.insertCart(cart);
		return "redirect:/index/cart.action";
	}

	// 查看购物车
	@RequestMapping("cart.action")
	public String cart(Map<String, Object> map) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) getRequest().getSession().getAttribute("userid");
		Cart cart = new Cart();
		cart.setUsersid(userid);
		List<Cart> cartList = this.cartDAO.getCartByCond(cart);
		map.put("cartList", cartList);
		return "users/cart";
	}

	// 删除购物车中的零食
	@RequestMapping("deletecart.action")
	public String deletecart(Map<String, Object> map, String id) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		this.cartDAO.deleteCart(id);
		return "redirect:/index/cart.action";
	}

	// 准备结算
	@RequestMapping("preCheckout.action")
	public String preCheckout(Map<String, Object> map) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) getRequest().getSession().getAttribute("userid");
		Cart cart = new Cart();
		cart.setUsersid(userid);
		List<Cart> cartList = this.cartDAO.getCartByCond(cart);
		if (cartList.size() == 0) {
			getRequest().setAttribute("message", "请选购商品");
			return "redirect:/index/cart.action";
		}
		return "users/checkout";
	}

	// 结算
	@RequestMapping("checkout.action")
	public String checkout(Map<String, Object> map) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) getRequest().getSession().getAttribute("userid");
		Cart cart1 = new Cart();
		cart1.setUsersid(userid);
		List<Cart> cartList = this.cartDAO.getCartByCond(cart1);
		if (cartList.size() == 0) {
			getRequest().setAttribute("message", "请选购商品");
			return "redirect:/index/cart.action";
		} else {
			// 获取一个1000-9999的随机数 防止同时提交
			String ordercode = "PD" + VeDate.getStringDatex();
			double total = 0;
			for (Cart cart : cartList) {
				Details details = new Details();
				details.setDetailsid(VeDate.getStringDatex() + (Math.random() * 9 + 1) * 1000);
				details.setGoodsid(cart.getGoodsid());
				details.setNum(cart.getNum());
				details.setOrdercode(ordercode);
				details.setPrice(cart.getPrice());
				this.detailsDAO.insertDetails(details);
				Goods goods = this.goodsDAO.getGoodsById(cart.getGoodsid());
				goods.setSellnum("" + (Integer.parseInt(goods.getSellnum()) + Integer.parseInt(cart.getNum())));
				goods.setStorage("" + (Integer.parseInt(goods.getStorage()) - Integer.parseInt(cart.getNum())));
				this.goodsDAO.updateGoods(goods);
				total += Double.parseDouble(cart.getPrice()) * Double.parseDouble(cart.getNum());
				this.cartDAO.deleteCart(cart.getCartid());
			}
			Orders orders = new Orders();
			orders.setAddress(getRequest().getParameter("address"));
			orders.setAddtime(VeDate.getStringDateShort());
			orders.setContact(getRequest().getParameter("contact"));
			orders.setOrdercode(ordercode);
			orders.setOrdersid(VeDate.getStringDatex());
			orders.setReceiver(getRequest().getParameter("receiver"));
			orders.setStatus("未付款");
			orders.setTotal("" + total);
			orders.setUsersid(userid);
			this.ordersDAO.insertOrders(orders);
		}
		return "redirect:/index/showOrders.action";
	}

	// 查看订购
	@RequestMapping("showOrders.action")
	public String showOrders(String number, Map<String, Object> map) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) getRequest().getSession().getAttribute("userid");
		Orders orders = new Orders();
		orders.setUsersid(userid);
		List<Orders> ordersList = new ArrayList<Orders>();
		List<Orders> tempList = this.ordersDAO.getOrdersByCond(orders);
		int pageNumber = tempList.size();
		int maxPage = pageNumber;
		if (maxPage % 10 == 0) {
			maxPage = maxPage / 10;
		} else {
			maxPage = maxPage / 10 + 1;
		}
		if (number == null) {
			number = "0";
		}
		int start = Integer.parseInt(number) * 10;
		int over = (Integer.parseInt(number) + 1) * 10;
		int count = pageNumber - over;
		if (count <= 0) {
			over = pageNumber;
		}
		for (int i = start; i < over; i++) {
			Orders o = tempList.get(i);
			ordersList.add(o);
		}
		String html = "";
		StringBuffer buffer = new StringBuffer();
		buffer.append("&nbsp;&nbsp;共为");
		buffer.append(maxPage);
		buffer.append("页&nbsp; 共有");
		buffer.append(pageNumber);
		buffer.append("条&nbsp; 当前为第");
		buffer.append((Integer.parseInt(number) + 1));
		buffer.append("页 &nbsp;");
		if ((Integer.parseInt(number) + 1) == 1) {
			buffer.append("首页");
		} else {
			buffer.append("<a href=\"index/showOrders.action?number=0\">首页</a>");
		}
		buffer.append("&nbsp;&nbsp;");
		if ((Integer.parseInt(number) + 1) == 1) {
			buffer.append("上一页");
		} else {
			buffer.append("<a href=\"index/showOrders.action?number=" + (Integer.parseInt(number) - 1) + "\">上一页</a>");
		}
		buffer.append("&nbsp;&nbsp;");
		if (maxPage <= (Integer.parseInt(number) + 1)) {
			buffer.append("下一页");
		} else {
			buffer.append("<a href=\"index/showOrders.action?number=" + (Integer.parseInt(number) + 1) + "\">下一页</a>");
		}
		buffer.append("&nbsp;&nbsp;");
		if (maxPage <= (Integer.parseInt(number) + 1)) {
			buffer.append("尾页");
		} else {
			buffer.append("<a href=\"index/showOrders.action?number=" + (maxPage - 1) + "\">尾页</a>");
		}
		html = buffer.toString();
		map.put("html", html);
		map.put("ordersList", ordersList);
		return "users/orderlist";
	}

	// 准备付款
	@RequestMapping("prePay.action")
	public String prePay(Map<String, Object> map, String id) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		map.put("id", id);
		return "users/pay";
	}

	// 付款
	@RequestMapping("pay.action")
	public String pay(Map<String, Object> map, String id) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		Orders orders = this.ordersDAO.getOrdersById(getRequest().getParameter("id"));
		orders.setStatus("已付款");
		this.ordersDAO.updateOrders(orders);
		return "redirect:/index/showOrders.action";
	}

	// 确认收货
	@RequestMapping("over.action")
	public String over(Map<String, Object> map, String id) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		System.out.println(getRequest().getParameter("id"));
		Orders orders = this.ordersDAO.getOrdersById(getRequest().getParameter("id"));
		orders.setStatus("已收货");
		this.ordersDAO.updateOrders(orders);
		return "redirect:/index/showOrders.action";
	}

	// 订单明细
	@RequestMapping("orderdetail.action")
	public String orderdetail(Map<String, Object> map, String id) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		Details details = new Details();
		details.setOrdercode(id);
		List<Details> detailsList = this.detailsDAO.getDetailsByCond(details);
		map.put("detailsList", detailsList);
		return "users/orderdetail";
	}

	// 准备登录
	@RequestMapping("preLogin.action")
	public String prelogin(Map<String, Object> map) {
		this.front(map);
		return "users/login";
	}

	// 用户登录
	@RequestMapping("login.action")
	public String login(Map<String, Object> map) {
		this.front(map);
		String username = getRequest().getParameter("username");
		String password = getRequest().getParameter("password");
		Users u = new Users();
		u.setUsername(username);
		List<Users> usersList = this.usersDAO.getUsersByCond(u);
		if (usersList.size() == 0) {
			getRequest().setAttribute("message", "用户名不存在");
			return "redirect:/index/prelogin.action";
		} else {
			Users users = usersList.get(0);
			if (password.equals(users.getPassword())) {
				getRequest().getSession().setAttribute("userid", users.getUsersid());
				getRequest().getSession().setAttribute("username", users.getUsername());
				getRequest().getSession().setAttribute("users", users);
			} else {
				getRequest().setAttribute("message", "密码错误");
				return "redirect:/index/prelogin.action";
			}
		}
		return "redirect:/index/usercenter.action";
	}

	// 准备注册
	@RequestMapping("preReg.action")
	public String preReg(Map<String, Object> map) {
		this.front(map);
		return "users/register";
	}

	// 用户注册
	@RequestMapping("register.action")
	public String register(Users users, Map<String, Object> map) {
		this.front(map);
		Users u = new Users();
		u.setUsername(users.getUsername());
		List<Users> usersList = this.usersDAO.getUsersByCond(users);
		if (usersList.size() == 0) {
			users.setUsersid(VeDate.getStringDatex());
			users.setRegdate(VeDate.getStringDateShort());
			this.usersDAO.insertUsers(users);
		} else {
			getRequest().setAttribute("message", "用户名已存在");
			return "redirect:/index/preReg.action";
		}
		return "redirect:/index/preLogin.action";
	}

	// 退出登录
	@RequestMapping("exit.action")
	public String exit(Map<String, Object> map) {
		this.front(map);
		getRequest().getSession().removeAttribute("userid");
		getRequest().getSession().removeAttribute("username");
		getRequest().getSession().removeAttribute("users");
		return "users/login";
	}

	// 用户中心
	@RequestMapping("usercenter.action")
	public String usercenter(Map<String, Object> map) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		return "users/usercenter";
	}

	// 用户中心
	@RequestMapping("userinfo.action")
	public String userinfo(Map<String, Object> map) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		return "users/userinfo";
	}

	// 修改个人信息
	@RequestMapping("personal.action")
	public String personal(Map<String, Object> map, Users users) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		this.usersDAO.updateUsers(users);
		getRequest().getSession().setAttribute("users", users);
		return "redirect:/index/userinfo.action";
	}

	// 准备修改密码
	@RequestMapping("prePwd.action")
	public String prePwd(Map<String, Object> map) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		return "users/editpwd";
	}

	// 修改密码
	@RequestMapping("editpwd.action")
	public String editpwd(Map<String, Object> map) {
		this.front(map);
		if (getRequest().getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) getRequest().getSession().getAttribute("userid");
		String password = getRequest().getParameter("password");
		String repassword = getRequest().getParameter("repassword");
		Users users = this.usersDAO.getUsersById(userid);
		if (password.equals(users.getPassword())) {
			users.setPassword(repassword);
			this.usersDAO.updateUsers(users);
		} else {
			getRequest().setAttribute("message", "旧密码错误");
			return "redirect:/index/prePwd.action";
		}
		return "redirect:/index/prePwd.action";
	}

	public UsersDAO getUsersDAO() {
		return usersDAO;
	}

	public void setUsersDAO(UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}

	public CateDAO getCateDAO() {
		return cateDAO;
	}

	public void setCateDAO(CateDAO cateDAO) {
		this.cateDAO = cateDAO;
	}

	public CartDAO getCartDAO() {
		return cartDAO;
	}

	public void setCartDAO(CartDAO cartDAO) {
		this.cartDAO = cartDAO;
	}

	public OrdersDAO getOrdersDAO() {
		return ordersDAO;
	}

	public void setOrdersDAO(OrdersDAO ordersDAO) {
		this.ordersDAO = ordersDAO;
	}

	public ArticleDAO getArticleDAO() {
		return articleDAO;
	}

	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}

	public DetailsDAO getDetailsDAO() {
		return detailsDAO;
	}

	public void setDetailsDAO(DetailsDAO detailsDAO) {
		this.detailsDAO = detailsDAO;
	}

	public GoodsDAO getGoodsDAO() {
		return goodsDAO;
	}

	public void setGoodsDAO(GoodsDAO goodsDAO) {
		this.goodsDAO = goodsDAO;
	}

}
