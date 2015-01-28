package htw.shark.nowdiscover.shoputils;

import htw.shark.nowdiscover.factory.*;

import java.util.*;

import net.sharkfw.knowledgeBase.*;

/**
 * 
 *
 */
public class SharkProduct implements Product {
	private static final String IS_RELATED_TO = "isRelatedTo";
	private static final String BELONGS_TO = "belongsTo";
	// SharkKB kb = ShopEngine.getKB();
	private SemanticNet sn;
	private SNSemanticTag productTag;
	private SharkKB kbase;

	// http://javarevisited.blogspot.de/2012/01/what-is-constructor-overloading-in-java.html
	public SharkProduct(SharkKB kb, String name, String url) {
		this(kb, name, new String[] { url });
		try {
			sn = kb.getTopicsAsSemanticNet();
			this.productTag = sn.createSemanticTag(name, url);
		} catch (SharkKBException e) {
			e.printStackTrace();
		}
	}

	public SharkProduct(SharkKB kb, String name, String... url) {
		try {
			sn = kb.getTopicsAsSemanticNet();
			this.productTag = sn.createSemanticTag(name, url);
		} catch (SharkKBException e) {
			e.printStackTrace();
		}
	}

	private SNSemanticTag getProductTag() {
		return productTag;
	}

	/**
	 * Adds related products to a product.
	 * 
	 * @param prods
	 *            - a list containing the related products.
	 * 
	 */
	public void addRelatedProducts(List<Product> prods) {
		for (Product product : prods) {
			if (product instanceof SharkProduct) {
				SharkProduct sProd = (SharkProduct) product;
				this.productTag.setPredicate(IS_RELATED_TO,
						sProd.getProductTag());
			} else {
				System.out.println("Something is wrong with your products!");
			}
		}
	}

	/**
	 * Gets the related products
	 * 
	 * @return a sorted list containing the related products.
	 */
	@Override
	public List<Product> getRelatedProducts() {
		List<Product> pList = new ArrayList();

		Enumeration<SNSemanticTag> enumTags = this.productTag
				.targetTags(SharkProduct.IS_RELATED_TO);

		while (enumTags != null && enumTags.hasMoreElements()) {
			SNSemanticTag aTag = enumTags.nextElement();
			pList.add(ShopEngine.getShopEngine().createProduct(aTag.getName(),
					aTag.getSI()));
		}
		Collections.sort(pList, new Comparator<Product>() {
			@Override
			public int compare(Product p1, Product p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});
		return pList;
	}

	/**
	 * Adds categories to a product.
	 * 
	 * @param categories
	 *            - a list containing the categories.
	 */
	@Override
	public void addCategories(List<Category> categories) {
		try {
			// sn = kbase.getTopicsAsSemanticNet();
			for (Category category : categories) {
				String arr[] = new String[category.getUrls().length];
				for (int i = 0; i < category.getUrls().length; i++) {
					arr[i] = category.getUrls()[i];
				}
				SNSemanticTag st = sn
						.createSemanticTag(category.getName(), arr);
				this.productTag.setPredicate(BELONGS_TO, st);
			}
		} catch (SharkKBException e1) {
			e1.printStackTrace();

		}

	}

	/**
	 * Gets the product categories.
	 * 
	 * @return - a sorted list conatining all the products categories.
	 */
	@Override
	public List<Category> getCategories() {
		List<Category> pList = new ArrayList();
		Enumeration<SNSemanticTag> enumTags = this.productTag
				.targetTags(SharkProduct.BELONGS_TO);
		while (enumTags != null && enumTags.hasMoreElements()) {
			SNSemanticTag aTag = enumTags.nextElement();
			Category c = null;
			try {
				c = new SharkCategory(kbase, aTag.getName(), aTag.getSI());
			} catch (SharkKBException e) {
				e.printStackTrace();
			}
			pList.add(c);
		}
		Collections.sort(pList, new Comparator<Category>() {
			@Override
			public int compare(Category c1, Category c2) {
				return c1.getName().compareTo(c2.getName());
			}
		});
		return pList;
	}

	/**
	 * Gets the product's name.
	 * 
	 * @return - the products name.
	 */
	@Override
	public String getName() {
		return this.getProductTag().getName();
	}

	/**
	 * Gets the product's urls.
	 */
	@Override
	public String[] getUrls() {
		return this.getProductTag().getSI();
	}

}