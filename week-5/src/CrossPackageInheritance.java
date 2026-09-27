class CrossPackageAccessRuleEngine {

    static String classifyAccess(String fieldModifier,
                                 String accessorContext) {

        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }

        fieldModifier = fieldModifier.toLowerCase();

        switch (fieldModifier) {

            case "private":
                return accessorContext.equals("SAME_CLASS")
                        ? "ALLOWED" : "DENIED";

            case "default":
                return (accessorContext.equals("SAME_CLASS") ||
                        accessorContext.equals("SAME_PACKAGE"))
                        ? "ALLOWED" : "DENIED";

            case "protected":
                if (accessorContext.equals("SAME_CLASS") ||
                        accessorContext.equals("SAME_PACKAGE") ||
                        accessorContext.equals(
                                "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {

                    return "ALLOWED";
                }

                return "DENIED";

            case "public":
                return "ALLOWED";

            default:
                return "DENIED";
        }
    }

    static String describeContext(String accessorContext) {

        if (accessorContext == null ||
                accessorContext.trim().isEmpty()) {
            return "";
        }

        String[] words = accessorContext.toLowerCase().split("_");
        StringBuilder result = new StringBuilder();

        for (String word : words) {

            if (word.isEmpty()) {
                continue;
            }

            result.append(
                    Character.toUpperCase(word.charAt(0))
            );

            if (word.length() > 1) {
                result.append(word.substring(1));
            }

            result.append(" ");
        }

        return result.toString().trim();
    }
}

public class CrossPackageInheritance {

    public static void main(String[] args) {

        System.out.println(
                CrossPackageAccessRuleEngine.classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));

        System.out.println(
                CrossPackageAccessRuleEngine.classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));

        System.out.println(
                CrossPackageAccessRuleEngine.describeContext(
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
    }
}