# All Checks

Check | Category | File Extensions | Description
----- | -------- | --------------- | -----------
AnnotationParameterOrderCheck | Miscellaneous | .java | |
[AnnotationUseStyleCheck](https://checkstyle.sourceforge.io/config_annotation.html#AnnotationUseStyle) | Styling | .java | Checks the style of elements in annotations. |
AnonymousClassCheck | Miscellaneous | .java | |
AppendCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
ArquillianCheck | Miscellaneous | .java | |
[ArrayCheck](checks/array_check.markdown#arraycheck) | Performance | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks if performance can be improved by using different mehods that can be used by collections |
[ArrayTypeStyleCheck](https://checkstyle.sourceforge.io/config_misc.html#ArrayTypeStyle) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks the style of array type definitions. |
AssertEqualsCheck | Miscellaneous | .java | |
AttributeOrderCheck | Miscellaneous | .java | |
[AvoidNestedBlocksCheck](https://checkstyle.sourceforge.io/config_blocks.html#AvoidNestedBlocks) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Finds nested blocks (blocks that are used freely in the code). |
[AvoidStarImportCheck](https://checkstyle.sourceforge.io/config_imports.html#AvoidStarImport) | Bug Prevention | .java | Checks that there are no import statements that use the * notation. |
[BNDBundleActivatorCheck](checks/bnd_bundle_activator_check.markdown#bndbundleactivatorcheck) | Bug Prevention | .bnd | Validates property value for `Bundle-Activator` |
BNDBundleCheck | Bug Prevention | .bnd | Validates `Liferay-Releng-*` properties |
[BNDBundleInformationCheck](checks/bnd_bundle_information_check.markdown#bndbundleinformationcheck) | Bug Prevention | .bnd | Validates property values for `Bundle-Version`, `Bundle-Name` and `Bundle-SymbolicName` |
BNDCapabilityCheck | Styling | .bnd | Sorts and applies logic to fix line breaks to property values for `Provide-Capability` and `Require-Capability` |
[BNDDefinitionKeysCheck](checks/bnd_definition_keys_check.markdown#bnddefinitionkeyscheck) | Bug Prevention | .bnd | Validates definition keys in `.bnd` files |
BNDDeprecatedAppBNDsCheck | Miscellaneous | .bnd | Checks for redundant `app.bnd` in deprecated or archived modules |
[BNDDirectoryNameCheck](checks/bnd_directory_name_check.markdown#bnddirectorynamecheck) | Bug Prevention | .bnd | Checks if the directory names of the submodules match the parent module name |
[BNDExportsCheck](checks/bnd_exports_check.markdown#bndexportscheck) | Bug Prevention | .bnd | Checks that modules not ending with `-api`, `-client`, `-spi`, `-tablig`, `-test-util` do not export packages |
BNDImportsCheck | Styling | .bnd | Sorts class names and checks for use of wildcards in property values for `-conditionalpackage`, `-exportcontents` and `Export-Package` |
[BNDIncludeResourceCheck](checks/bnd_include_resource_check.markdown#bndincluderesourcecheck) | Bug Prevention | .bnd | Checks for unnesecarry including of `test-classes/integration` |
BNDLineBreaksCheck | Styling | .bnd | Checks for incorrect/missing line breaks |
BNDMultipleAppBNDsCheck | Bug Prevention | .bnd | Checks for duplicate `app.bnd` (when both `/apps/` and `/apps/dxp/` contain the same module) |
BNDRangeCheck | Bug Prevention | .bnd | Checks for use or range expressions |
BNDRunInstructionsOrderCheck | Styling | .bndrun | Sorts definition keys alphabetically |
[BNDSchemaVersionCheck](checks/bnd_schema_version_check.markdown#bndschemaversioncheck) | Bug Prevention | .bnd | Checks for incorrect use of property `Liferay-Require-SchemaVersion` |
BNDStylingCheck | Styling | .bnd | Applies rules to enforce consisteny in code style |
BNDSuiteCheck | Miscellaneous | .bnd | Checks that deprecated apps are moved to the `archived` folder |
[BNDWebContextPathCheck](checks/bnd_web_context_path_check.markdown#bndwebcontextpathcheck) | Bug Prevention | .bnd | Checks if the property value for `Web-ContextPath` matches the module directory |
BNDWhitespaceCheck | Styling | .bnd | Checks for incorrect/missing line whitespace |
CDNCheck | Miscellaneous | | |
CQLKeywordCheck | Miscellaneous | .cql | |
CSSCommentsCheck | Miscellaneous | .css or .scss | |
CSSEmptyLinesCheck | Miscellaneous | .css or .scss | |
CSSHexColorsCheck | Miscellaneous | .css or .scss | |
CSSImportsCheck | Miscellaneous | .css or .scss | |
CSSPropertiesOrderCheck | Miscellaneous | .css or .scss | |
CamelCaseNameCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[ChainingCheck](checks/chaining_check.markdown#chainingcheck) | Styling | .java | Checks that chaining is only applied on certain types and methods |
CodeownersFileLocationCheck | Miscellaneous | CODEOWNERS | |
CodeownersWhitespaceCheck | Miscellaneous | CODEOWNERS | |
CompatClassImportsCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
ConcatCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
ConfigDefinitionKeysCheck | Miscellaneous | .cfg or .config | |
ConstantNameCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
ConstructorMissingEmptyLineCheck | Miscellaneous | .java | |
ContractionsCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
CopyrightCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
CreationMenuBuilderCheck | Miscellaneous | .java | |
[DefaultComesLastCheck](https://checkstyle.sourceforge.io/config_coding.html#DefaultComesLast) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Check that the default is after all the cases in a switch statement. |
DeprecatedUsageCheck | Miscellaneous | .java | |
DockerfileEmptyLinesCheck | Miscellaneous | Dockerfile | |
DockerfileInstructionCheck | Miscellaneous | Dockerfile | |
EmptyCollectionCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
EnumConstantDividerCheck | Miscellaneous | .java | |
EnumConstantOrderCheck | Miscellaneous | .java | |
ExceptionMessageCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
ExceptionVariableNameCheck | Miscellaneous | .java | |
FTLEmptyLinesCheck | Miscellaneous | .ftl | |
FTLIfStatementCheck | Miscellaneous | .ftl | |
FTLImportsCheck | Miscellaneous | .ftl | |
FTLLiferayVariableOrderCheck | Miscellaneous | .ftl | |
FTLStringRelationalOperatorCheck | Miscellaneous | .ftl | |
FTLStylingCheck | Miscellaneous | .ftl | |
FTLTagAttributesCheck | Miscellaneous | .ftl | |
FTLTagCheck | Miscellaneous | .ftl | |
FTLWhitespaceCheck | Miscellaneous | .ftl | |
FactoryCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
FilterStringWhitespaceCheck | Miscellaneous | .java | |
[FrameworkBundleCheck](checks/framework_bundle_check.markdown#frameworkbundlecheck) | Performance | .java | Checks that `org.osgi.framework.Bundle.getHeaders()` is not used |
FullyQualifiedNameCheck | Miscellaneous | .java | |
GetterUtilCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
GradleBlockOrderCheck | Miscellaneous | .gradle | |
GradleBodyCheck | Miscellaneous | .gradle | |
GradleDependenciesCheck | Miscellaneous | .gradle | |
[GradleDependencyArtifactsCheck](checks/gradle_dependency_artifacts_check.markdown#gradledependencyartifactscheck) | Bug Prevention | .gradle | Checks that value `default` is not used for attribute `version` |
GradleDependencyConfigurationCheck | Miscellaneous | .gradle | |
GradleDependencyVersionCheck | Miscellaneous | .gradle | |
GradleExportedPackageDependenciesCheck | Miscellaneous | .gradle | |
GradleImportsCheck | Miscellaneous | .gradle | |
GradleIndentationCheck | Miscellaneous | .gradle | |
GradleJavaVersionCheck | Miscellaneous | .gradle | |
GradlePropertiesCheck | Miscellaneous | .gradle | |
GradleProvidedDependenciesCheck | Miscellaneous | .gradle | |
GradleRequiredDependenciesCheck | Miscellaneous | .gradle | |
GradleStylingCheck | Miscellaneous | .gradle | |
[GradleTaskCreationCheck](checks/gradle_task_creation_check.markdown#gradletaskcreationcheck) | Styling | .gradle | Checks that a task is declared on a separate line before the closure |
GradleTestDependencyVersionCheck | Miscellaneous | .gradle | |
HTMLEmptyLinesCheck | Miscellaneous | .html | |
HTMLWhitespaceCheck | Miscellaneous | .html | |
[IfStatementCheck](checks/if_statement_check.markdown#ifstatementcheck) | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
IncorrectFileLocationCheck | Miscellaneous | | |
InstanceofOrderCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
JSLodashDependencyCheck | Miscellaneous | .js or .jsx | |
JSONDeprecatedPackagesCheck | Miscellaneous | .json or .npmbridgerc | |
JSONIndentationCheck | Miscellaneous | .json or .npmbridgerc | |
JSONLineBreakCheck | Miscellaneous | .json or .npmbridgerc | |
JSONNamingCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
JSONPackageJSONBNDVersionCheck | Miscellaneous | .json or .npmbridgerc | |
JSONPackageJSONCheck | Miscellaneous | .json or .npmbridgerc | |
JSONPackageJSONDependencyVersionCheck | Miscellaneous | .json or .npmbridgerc | |
JSONPropertyOrderCheck | Miscellaneous | .json or .npmbridgerc | |
[JSONUtilCheck](checks/json_util_check.markdown#jsonutilcheck) | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
JSONValidationCheck | Miscellaneous | .json or .npmbridgerc | |
JSONWhitespaceCheck | Miscellaneous | .json or .npmbridgerc | |
[JSPArrowFunctionCheck](checks/jsp_arrow_function_check.markdown#jsparrowfunctioncheck) | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPButtonTagCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPDefineObjectsCheck](checks/jsp_define_objects_check.markdown#jspdefineobjectscheck) | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPEmptyLinesCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPExceptionOrderCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPFunctionNameCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPIllegalSyntaxCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPImportsCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPIncludeCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPIndentationCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPLanguageKeysCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPLanguageUtilCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPLineBreakCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPLogFileNameCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPLogParametersCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPMissingTaglibsCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPModuleIllegalImportsCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPParenthesesCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPRedirectBackURLCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
[JSPSendRedirectCheck](checks/jsp_send_redirect_check.markdown#jspsendredirectcheck) | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPServiceUtilCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPSessionKeysCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPStringBundlerCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPStringMethodsCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPStylingCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPSubnameCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPTagAttributesCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPTaglibVariableCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPUnusedJSPF | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPUnusedTermsCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPWhitespaceCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSPXSSVulnerabilitiesCheck | Miscellaneous | .jsp, .jspf, .tag, .tpl or .vm | |
JSStylingCheck | Miscellaneous | .js or .jsx | |
JSWhitespaceCheck | Miscellaneous | .js or .jsx | |
Java2HTMLCheck | Miscellaneous | .java | |
[JavaAPISignatureCheck](checks/java_api_signature_check.markdown#javaapisignaturecheck) | Bug Prevention | .java | Checks that types `HttpServletRequest`, `HttpServletResponse`, `ThemeDisplay`, and `ServiceContext` are not used in API method signatures |
JavaAbstractMethodCheck | Miscellaneous | .java | |
JavaAggregateTestRuleParameterOrderCheck | Miscellaneous | .java | |
JavaAnnotationDefaultAttributeCheck | Miscellaneous | .java | |
JavaAnnotationsCheck | Miscellaneous | .java | |
JavaAnonymousInnerClassCheck | Miscellaneous | .java | |
JavaAssertEqualsCheck | Miscellaneous | .java | |
JavaBooleanStatementCheck | Miscellaneous | .java | |
JavaBooleanUsageCheck | Miscellaneous | .java | |
JavaCleanUpMethodSuperCleanUpCheck | Miscellaneous | .java | |
[JavaCleanUpMethodVariablesCheck](checks/java_clean_up_method_variables_check.markdown#javacleanupmethodvariablescheck) | Bug Prevention | .java | Checks that variables in `Tag` classes get cleaned up properly |
[JavaCollatorUtilCheck](checks/java_collator_util_check.markdown#javacollatorutilcheck) | Bug Prevention | .java | Checks for correct use of `Collator` |
JavaComponentActivateCheck | Miscellaneous | .java | |
JavaComponentAnnotationsCheck | Miscellaneous | .java | |
[JavaConfigurationAdminCheck](checks/java_configuration_admin_check.markdown#javaconfigurationadmincheck) | Bug Prevention | .java | Checks for correct use of `location == ?` when calling `org.osgi.service.cm.ConfigurationAdmin#createFactoryConfiguration` |
[JavaConfigurationCategoryCheck](checks/java_configuration_category_check.markdown#javaconfigurationcategorycheck) | Bug Prevention | .java | Checks that the value of `category` in `@ExtendedObjectClassDefinition` matches the `categoryKey` of the corresponding class in `configuration-admin-web` |
[JavaConstructorParametersCheck](checks/java_constructor_parameters_check.markdown#javaconstructorparameterscheck) | Styling | .java | Checks that the order of variable assignments matches the order of the parameters in the constructor signature |
JavaConstructorSuperCallCheck | Miscellaneous | .java | |
JavaDataAccessConnectionCheck | Miscellaneous | .java | |
JavaDeprecatedJavadocCheck | Miscellaneous | .java | |
JavaDeprecatedKernelClassesCheck | Miscellaneous | .java | |
JavaDeserializationSecurityCheck | Miscellaneous | .java | |
JavaDiamondOperatorCheck | Miscellaneous | .java | |
JavaElseStatementCheck | Miscellaneous | .java | |
JavaEmptyLineAfterSuperCallCheck | Miscellaneous | .java | |
JavaEmptyLinesCheck | Miscellaneous | .java | |
JavaExceptionCheck | Miscellaneous | .java | |
[JavaFinderCacheCheck](checks/java_finder_cache_check.markdown#javafindercachecheck) | Bug Prevention | .java | Checks that the method `BasePersistenceImpl.fetchByPrimaryKey` is overridden, when using `FinderPath` |
JavaFinderImplCustomSQLCheck | Miscellaneous | .java | |
[JavaForLoopCheck](checks/java_for_loop_check.markdown#javaforloopcheck) | Styling | .java | Checks if a Enhanced For Loop can be used instead of a Simple For Loop |
JavaHibernateSQLCheck | Miscellaneous | .java | |
JavaIOExceptionCheck | Miscellaneous | .java | |
JavaIllegalImportsCheck | Miscellaneous | .java | |
JavaImportsCheck | Miscellaneous | .java | |
[JavaIndexableCheck](checks/java_indexable_check.markdown#javaindexablecheck) | Bug Prevention | .java | Checks that the type gets returned when using annotation `@Indexable` |
JavaInnerClassImportsCheck | Miscellaneous | .java | |
JavaInterfaceCheck | Miscellaneous | .java | |
JavaInternalPackageCheck | Miscellaneous | .java | |
JavaJSPDynamicIncludeCheck | Miscellaneous | .java | |
[JavaLocalSensitiveComparisonCheck](checks/java_local_sensitive_comparison_check.markdown#javalocalsensitivecomparisoncheck) | Bug Prevention | .java | Checks that `java.text.Collator` is used when comparing localized values |
JavaLogClassNameCheck | Miscellaneous | .java | |
JavaLogLevelCheck | Miscellaneous | .java | |
JavaLogParametersCheck | Miscellaneous | .java | |
JavaLongLinesCheck | Miscellaneous | .java | |
JavaMapBuilderGenericsCheck | Miscellaneous | .java | |
[JavaMetaAnnotationsCheck](checks/java_meta_annotations_check.markdown#javametaannotationscheck) | Bug Prevention | .java | Checks for correct use of attributes `description` and `name` in annotation `@aQute.bnd.annotation.metatype.Meta` |
JavaModifiedServiceMethodCheck | Miscellaneous | .java | |
[JavaModuleComponentCheck](checks/java_module_component_check.markdown#javamodulecomponentcheck) | Bug Prevention | .java | Checks for use of `@Component` in `-api` or `-spi` modules |
[JavaModuleExposureCheck](checks/java_module_exposure_check.markdown#javamoduleexposurecheck) | Bug Prevention | .java | Checks for exposure of SPI types in API |
JavaModuleExtendedObjectClassDefinitionCheck | Miscellaneous | .java | |
JavaModuleIllegalImportsCheck | Miscellaneous | .java | |
JavaModuleInternalImportsCheck | Miscellaneous | .java | |
JavaModuleJavaxPortletInitParamTemplatePathCheck | Miscellaneous | .java | |
JavaModuleServiceProxyFactoryCheck | Miscellaneous | .java | |
JavaModuleServiceReferenceCheck | Miscellaneous | .java | |
[JavaModuleTestCheck](checks/java_module_test_check.markdown#javamoduletestcheck) | Bug Prevention | .java | Checks package names in tests |
[JavaMultiPlusConcatCheck](checks/java_multi_plus_concat_check.markdown#javamultiplusconcatcheck) | Performance | .java | Checks that we do not concatenate more than 3 String objects |
JavaOSGiReferenceCheck | Miscellaneous | .java | |
[JavaPackagePathCheck](checks/java_package_path_check.markdown#javapackagepathcheck) | Bug Prevention | .java | Checks that the package name matches the file location |
JavaParameterAnnotationsCheck | Miscellaneous | .java | |
[JavaProcessCallableCheck](checks/java_process_callable_check.markdown#javaprocesscallablecheck) | Bug Prevention | .java | Checks that a class implementing `ProcessCallable` assigns a `serialVersionUID` |
JavaProviderTypeAnnotationCheck | Miscellaneous | .java | |
JavaRedundantConstructorCheck | Miscellaneous | .java | |
JavaReleaseInfoCheck | Miscellaneous | .java | |
[JavaResultSetCheck](checks/java_result_set_check.markdown#javaresultsetcheck) | Bug Prevention | .java | Checks for correct use `java.sql.ResultSet.getInt(int)` |
JavaReturnStatementCheck | Miscellaneous | .java | |
[JavaSeeAnnotationCheck](checks/java_see_annotation_check.markdown#javaseeannotationcheck) | Bug Prevention | .java | Checks for nested annotations inside `@see` |
JavaServiceImplCheck | Miscellaneous | .java | |
JavaServiceObjectCheck | Miscellaneous | .java | |
JavaServiceTrackerFactoryCheck | Miscellaneous | .java | |
[JavaServiceUtilCheck](checks/java_service_util_check.markdown#javaserviceutilcheck) | Miscellaneous | .java | |
JavaSessionCheck | Miscellaneous | .java | |
JavaSignatureParametersCheck | Miscellaneous | .java | |
JavaStagedModelDataHandlerCheck | Miscellaneous | .java | |
JavaStaticBlockCheck | Miscellaneous | .java | |
[JavaStaticImportsCheck](checks/java_static_imports_check.markdown#javastaticimportscheck) | Miscellaneous | .java | |
JavaStaticVariableDependencyCheck | Miscellaneous | .java | |
[JavaStopWatchCheck](checks/java_stop_watch_check.markdown#javastopwatchcheck) | Miscellaneous | .java | |
JavaStringBundlerConcatCheck | Miscellaneous | .java | |
JavaStringBundlerInitialCapacityCheck | Miscellaneous | .java | |
JavaStylingCheck | Miscellaneous | .java | |
JavaSwitchCheck | Miscellaneous | .java | |
JavaSystemEventAnnotationCheck | Miscellaneous | .java | |
JavaSystemExceptionCheck | Miscellaneous | .java | |
JavaTaglibMethodCheck | Miscellaneous | .java | |
JavaTermDividersCheck | Miscellaneous | .java | |
JavaTermOrderCheck | Miscellaneous | .java | |
JavaTermStylingCheck | Miscellaneous | .java | |
[JavaTestMethodAnnotationsCheck](checks/java_test_method_annotations_check.markdown#javatestmethodannotationscheck) | Naming Conventions | .java | Checks if methods with test annotations follow the naming conventions |
[JavaUnsafeCastingCheck](checks/java_unsafe_casting_check.markdown#javaunsafecastingcheck) | Bug Prevention | .java | Checks for potential ClassCastException |
JavaUnusedSourceFormatterChecksCheck | Miscellaneous | .java | |
[JavaUpgradeClassCheck](checks/java_upgrade_class_check.markdown#javaupgradeclasscheck) | Miscellaneous | .java | |
JavaUpgradeConnectionCheck | Miscellaneous | .java | |
JavaUpgradeVersionCheck | Miscellaneous | .java | |
JavaVariableTypeCheck | Miscellaneous | .java | |
JavaVerifyUpgradeConnectionCheck | Miscellaneous | .java | |
JavaXMLSecurityCheck | Miscellaneous | .java | |
JavadocCheck | Miscellaneous | .java | |
[JavadocStyleCheck](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocStyle) | Styling | .java | Validates Javadoc comments to help ensure they are well formed. |
LFRBuildContentCheck | Miscellaneous | .lfrbuild-* | |
LFRBuildReadmeCheck | Miscellaneous | .lfrbuild-* | |
LPS42924Check | Miscellaneous | .java | |
[LambdaCheck](checks/lambda_check.markdown#lambdacheck) | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
LanguageKeysCheck | Miscellaneous | .java, .js or .jsx | |
LineBreakAfterCommaCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
LineBreakBeforeGenericStartCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
LineEndCharacterCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[ListUtilCheck](checks/list_util_check.markdown#listutilcheck) | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
LiteralStringEqualsCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[LocalFinalVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#LocalFinalVariableName) | Naming Conventions | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that local final variable names conform to a specified pattern. |
LocalPatternCheck | Miscellaneous | .java | |
[LocalVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#LocalVariableName) | Naming Conventions | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that local, non-final variable names conform to a specified pattern. |
LocaleUtilCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
LogMessageCheck | Miscellaneous | .java | |
MapBuilderCheck | Miscellaneous | .java | |
[MapIterationCheck](checks/map_iteration_check.markdown#mapiterationcheck) | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
MarkdownFileExtensionCheck | Miscellaneous | .markdown or .md | |
MarkdownSourceFormatterDocumentationCheck | Miscellaneous | .markdown or .md | |
MarkdownSourceFormatterReadmeCheck | Miscellaneous | .markdown or .md | |
MarkdownStylingCheck | Miscellaneous | .markdown or .md | |
MarkdownWhitespaceCheck | Miscellaneous | .markdown or .md | |
[MemberNameCheck](https://checkstyle.sourceforge.io/config_naming.html#MemberName) | Naming Conventions | .java, .java, .jsp, .jsp, .jspf, .jspf, .tag, .tag, .tpl, .tpl, .vm or .vm | Checks that instance variable names conform to a specified pattern. |
MethodCallsOrderCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[MethodNameCheck](https://checkstyle.sourceforge.io/config_naming.html#MethodName) | Naming Conventions | .java, .java, .jsp, .jsp, .jspf, .jspf, .tag, .tag, .tpl, .tpl, .vm or .vm | Checks that method names conform to a specified pattern. |
MethodNamingCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[MethodParamPadCheck](https://checkstyle.sourceforge.io/config_whitespace.html#MethodParamPad) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks the padding between the identifier of a method definition, constructor definition, method call, or constructor invocation; and the left parenthesis of the parameter list. |
MissingAuthorCheck | Miscellaneous | .java | |
[MissingDeprecatedCheck](https://checkstyle.sourceforge.io/config_annotation.html#MissingDeprecated) | Styling | .java | Verifies that the annotation @Deprecated and the Javadoc tag @deprecated are both present when either of them is present. |
MissingDeprecatedJavadocCheck | Miscellaneous | .java | |
MissingDiamondOperatorCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[MissingEmptyLineCheck](checks/missing_empty_line_check.markdown#missingemptylinecheck) | Miscellaneous | .java | |
MissingModifierCheck | Miscellaneous | .java | |
MissingOverrideCheck | Miscellaneous | .java | |
MissingParenthesesCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[ModifierOrderCheck](https://checkstyle.sourceforge.io/config_modifier.html#ModifierOrder) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that the order of modifiers conforms to the suggestions in the Java Language specification, § 8.1.1, 8.3.1, 8.4.3 and 9.4. |
[MultipleVariableDeclarationsCheck](https://checkstyle.sourceforge.io/config_coding.html#MultipleVariableDeclarations) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that each variable declaration is in its own statement and on its own line. |
NewFileCheck | Miscellaneous | | |
[NoLineWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoLineWrap) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that chosen statements are not line-wrapped. |
[NoWhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceAfter) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there is no whitespace after a token. |
[NoWhitespaceBeforeCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceBefore) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there is no whitespace before a token. |
NonbreakingSpaceCheck | Miscellaneous | | |
NotRequireThisCheck | Miscellaneous | .java | |
NumberSuffixCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[OneStatementPerLineCheck](https://checkstyle.sourceforge.io/config_coding.html#OneStatementPerLine) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that there is only one statement per line. |
OperatorOperandCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
OperatorOrderCheck | Miscellaneous | .java | |
[OperatorWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#OperatorWrap) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks the policy on how to wrap lines on operators. |
[PackageNameCheck](https://checkstyle.sourceforge.io/config_naming.html#PackageName) | Naming Conventions | .java | Checks that package names conform to a specified pattern. |
PackageinfoBNDExportPackageCheck | Miscellaneous | packageinfo | |
[ParameterNameCheck](https://checkstyle.sourceforge.io/config_naming.html#ParameterName) | Naming Conventions | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that method parameter names conform to a specified pattern. |
ParsePrimitiveTypeCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PersistenceCallCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[PersistenceUpdateCheck](checks/persistence_update_check.markdown#persistenceupdatecheck) | Miscellaneous | .java | |
PlusStatementCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PoshiAnnotationsOrderCheck | Miscellaneous | .function, .macro or .testcase | |
PoshiCommandsOrderCheck | Miscellaneous | .function, .macro or .testcase | |
PoshiEmptyLinesCheck | Miscellaneous | .function, .macro or .testcase | |
PoshiIndentationCheck | Miscellaneous | .function, .macro or .testcase | |
PoshiParametersOrderCheck | Miscellaneous | .function, .macro or .testcase | |
PoshiStylingCheck | Miscellaneous | .function, .macro or .testcase | |
PoshiWhitespaceCheck | Miscellaneous | .function, .macro or .testcase | |
PrimitiveWrapperInstantiationCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PrincipalExceptionCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
PropertiesBuildIncludeDirsCheck | Miscellaneous | .properties | |
PropertiesCommentsCheck | Miscellaneous | .properties | |
PropertiesDefinitionKeysCheck | Miscellaneous | .properties | |
PropertiesDependenciesFileCheck | Miscellaneous | .properties | |
PropertiesEmptyLinesCheck | Miscellaneous | .properties | |
PropertiesImportedFilesContentCheck | Miscellaneous | .properties | |
[PropertiesLanguageKeysCheck](checks/properties_language_keys_check.markdown#propertieslanguagekeyscheck) | Miscellaneous | .properties | |
PropertiesLanguageKeysOrderCheck | Miscellaneous | .properties | |
PropertiesLiferayPluginPackageFileCheck | Miscellaneous | .properties | |
PropertiesLiferayPluginPackageLiferayVersionsCheck | Miscellaneous | .properties | |
PropertiesLongLinesCheck | Miscellaneous | .properties | |
PropertiesPortalEnvironmentVariablesCheck | Miscellaneous | .properties | |
PropertiesPortalFileCheck | Miscellaneous | .properties | |
PropertiesPortletFileCheck | Miscellaneous | .properties | |
PropertiesServiceKeysCheck | Miscellaneous | .properties | |
PropertiesSourceFormatterContentCheck | Miscellaneous | .properties | |
PropertiesSourceFormatterFileCheck | Miscellaneous | .properties | |
PropertiesStylingCheck | Miscellaneous | .properties | |
PropertiesVerifyPropertiesCheck | Miscellaneous | .properties | |
PropertiesWhitespaceCheck | Miscellaneous | .properties | |
RedundantBranchingStatementCheck | Miscellaneous | .java | |
RedundantVariableDeclarationCheck | Miscellaneous | .java | |
ReferenceAnnotationCheck | Miscellaneous | .java | |
[RequireThisCheck](https://checkstyle.sourceforge.io/config_coding.html#RequireThis) | Bug Prevention | .java | Checks that references to instance variables and methods of the present object are explicitly of the form 'this.varName' or 'this.methodName(args)' and that those references don't rely on the default behavior when 'this.' is absent. |
[ResourceBundleCheck](checks/resource_bundle_check.markdown#resourcebundlecheck) | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
SQLEmptyLinesCheck | Miscellaneous | .sql | |
[SQLLongNamesCheck](checks/sql_long_names_check.markdown#sqllongnamescheck) | Bug Prevention | .sql | Checks for table and column names that exceed 30 characters |
SQLStylingCheck | Miscellaneous | .sql | |
SelfReferenceCheck | Miscellaneous | .java | |
SemiColonCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
SessionKeysCheck | Miscellaneous | .java | |
SingleStatementClauseCheck | Miscellaneous | .java | |
SizeIsZeroCheck | Miscellaneous | .java | |
SlantedQuotesCheck | Miscellaneous | | |
SoyEmptyLinesCheck | Miscellaneous | .soy | |
[StaticBlockCheck](checks/static_block_check.markdown#staticblockcheck) | Miscellaneous | .java | |
[StaticVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#StaticVariableName) | Naming Conventions | .java, .java, .jsp, .jsp, .jspf, .jspf, .tag, .tag, .tpl, .tpl, .vm or .vm | Checks that static, non-final variable names conform to a specified pattern. |
StringBundlerNamingCheck | Naming Conventions | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks for consistent naming on variables of type 'StringBundler' |
StringCastCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
[StringLiteralEqualityCheck](https://checkstyle.sourceforge.io/config_coding.html#StringLiteralEquality) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that string literals are not used with == or !=. |
[StringMethodsCheck](checks/string_methods_check.markdown#stringmethodscheck) | Performance | .java | Checks if performance can be improved by using different String operation methods |
SubstringCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
TLDElementOrderCheck | Miscellaneous | .tld | |
TLDTypeCheck | Miscellaneous | .tld | |
TernaryOperatorCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
TestClassCheck | Miscellaneous | .java | |
ThreadLocalUtilCheck | Miscellaneous | .java | |
ThreadNameCheck | Miscellaneous | .java | |
TransactionalTestRuleCheck | Miscellaneous | .java | |
[TypeNameCheck](https://checkstyle.sourceforge.io/config_naming.html#TypeName) | Naming Conventions | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that type names conform to a specified pattern. |
UnnecessaryAssignCheck | Miscellaneous | .java | |
[UnnecessaryParenthesesCheck](https://checkstyle.sourceforge.io/config_coding.html#UnnecessaryParentheses) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks if unnecessary parentheses are used in a statement or expression. |
UnparameterizedClassCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
UnprocessedExceptionCheck | Miscellaneous | .java | |
UnusedMethodCheck | Miscellaneous | .java | |
UnusedParameterCheck | Miscellaneous | .java | |
UnusedVariableCheck | Miscellaneous | .java | |
UnwrappedVariableInfoCheck | Miscellaneous | .java | |
[ValidatorEqualsCheck](checks/validator_equals_check.markdown#validatorequalscheck) | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
ValidatorIsNullCheck | Miscellaneous | .java, .jsp, .jspf, .tag, .tpl or .vm | |
VariableDeclarationAsUsedCheck | Miscellaneous | .java | |
VariableNameCheck | Miscellaneous | .java | |
[WhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAfter) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that a token is followed by whitespace, with the exception that it does not check for whitespace after the semicolon of an empty for iterator. |
WhitespaceAfterParameterAnnotationCheck | Miscellaneous | .java | |
[WhitespaceAroundCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAround) | Styling | .java, .jsp, .jspf, .tag, .tpl or .vm | Checks that a token is surrounded by whitespace. |
WhitespaceAroundGenericsCheck | Miscellaneous | .java | |
WhitespaceCheck | Miscellaneous | .cfg, .config, .cql, .css, .dtd, .gradle, .groovy, .scss, .soy, .sql, .tld, .ts, Dockerfile or packageinfo | |
XMLBuildFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLCheckstyleFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLCustomSQLOrderCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLCustomSQLStylingCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLDDLStructuresFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLEmptyLinesCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLFSBExcludeFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLFriendlyURLRoutesFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLHBMFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLIndentationCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .tld, .toggle, .wsdl, .xml or .xsd | |
XMLIvyFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLLog4jFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLLookAndFeelCompatibilityVersionCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLLookAndFeelFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLModelHintsFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLPomFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLPortletFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLPortletPreferencesFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLPoshiFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLProjectElementCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLResourceActionsFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
[XMLServiceEntityNameCheck](checks/xml_service_entity_name_check.markdown#xmlserviceentitynamecheck) | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLServiceFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLServiceReferenceCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLSolrSchemaFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLSourcechecksFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLSpringFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLStrutsConfigFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLStylingCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLSuppressionsFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLTagAttributesCheck | Miscellaneous | .action, .function, .html, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLTestIgnorableErrorLinesFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLTilesDefsFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLToggleFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLWebFileCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
XMLWhitespaceCheck | Miscellaneous | .action, .function, .jrxml, .macro, .pom, .testcase, .toggle, .wsdl, .xml or .xsd | |
YMLDefinitionOrderCheck | Miscellaneous | .yaml or .yml | |
YMLEmptyLinesCheck | Miscellaneous | .yaml or .yml | |
YMLLongLinesCheck | Miscellaneous | .yaml or .yml | |
YMLStylingCheck | Miscellaneous | .yaml or .yml | |
YMLWhitespaceCheck | Miscellaneous | .yaml or .yml | |