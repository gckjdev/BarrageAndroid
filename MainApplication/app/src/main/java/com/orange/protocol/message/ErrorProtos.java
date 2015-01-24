// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Error.proto

package com.orange.protocol.message;

public final class ErrorProtos {
  private ErrorProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  /**
   * Protobuf enum {@code barrage.PBError}
   */
  public enum PBError
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>ERROR_READ_POST_DATA = 1000001;</code>
     *
     * <pre>
     * system
     * </pre>
     */
    ERROR_READ_POST_DATA(0, 1000001),
    /**
     * <code>ERROR_PARSE_POST_DATA = 1000002;</code>
     */
    ERROR_PARSE_POST_DATA(1, 1000002),
    /**
     * <code>ERROR_NO_SERVICE_FOR_TYPE = 1000003;</code>
     */
    ERROR_NO_SERVICE_FOR_TYPE(2, 1000003),
    /**
     * <code>ERROR_SERVICE_CATCH_EXCEPTION = 1000004;</code>
     */
    ERROR_SERVICE_CATCH_EXCEPTION(3, 1000004),
    /**
     * <code>ERROR_UNKNOWN = 1000005;</code>
     */
    ERROR_UNKNOWN(4, 1000005),
    /**
     * <code>ERROR_DATA_RESPONSE_NULL = 1000006;</code>
     */
    ERROR_DATA_RESPONSE_NULL(5, 1000006),
    /**
     * <code>ERROR_JSON_EXCEPTION = 1000007;</code>
     */
    ERROR_JSON_EXCEPTION(6, 1000007),
    /**
     * <code>ERROR_INCORRECT_INPUT_DATA = 1000008;</code>
     */
    ERROR_INCORRECT_INPUT_DATA(7, 1000008),
    /**
     * <code>ERROR_USER_LOGIN_UNKNOWN_TYPE = 2000001;</code>
     *
     * <pre>
     * user
     * </pre>
     */
    ERROR_USER_LOGIN_UNKNOWN_TYPE(8, 2000001),
    /**
     * <code>ERROR_USER_LOGIN_INFO_EMPTY = 2000002;</code>
     */
    ERROR_USER_LOGIN_INFO_EMPTY(9, 2000002),
    /**
     * <code>ERROR_USER_REGISTER_UNKNOWN_TYPE = 2000003;</code>
     */
    ERROR_USER_REGISTER_UNKNOWN_TYPE(10, 2000003),
    /**
     * <code>ERROR_USER_REGISTER_INFO_EMPTY = 2000004;</code>
     */
    ERROR_USER_REGISTER_INFO_EMPTY(11, 2000004),
    /**
     * <code>ERROR_USER_REGISTER_INVALID_INVITE_CODE = 2000005;</code>
     */
    ERROR_USER_REGISTER_INVALID_INVITE_CODE(12, 2000005),
    /**
     * <code>ERROR_USER_NOT_FOUND = 2000006;</code>
     */
    ERROR_USER_NOT_FOUND(13, 2000006),
    /**
     * <code>ERROR_FRIEND_NOT_FOUND = 2000007;</code>
     */
    ERROR_FRIEND_NOT_FOUND(14, 2000007),
    /**
     * <code>ERROR_FRIEND_NOT_ALLOW_ADD_ME = 2000008;</code>
     */
    ERROR_FRIEND_NOT_ALLOW_ADD_ME(15, 2000008),
    /**
     * <code>ERROR_INVITE_CODE_NOT_EXIST = 2000009;</code>
     */
    ERROR_INVITE_CODE_NOT_EXIST(16, 2000009),
    /**
     * <code>ERROR_INVITE_CODE_USED = 2000010;</code>
     */
    ERROR_INVITE_CODE_USED(17, 2000010),
    /**
     * <code>ERROR_EMAIL_EMPTY = 2000011;</code>
     */
    ERROR_EMAIL_EMPTY(18, 2000011),
    /**
     * <code>ERROR_MOBILE_EMPTY = 2000012;</code>
     */
    ERROR_MOBILE_EMPTY(19, 2000012),
    /**
     * <code>ERROR_SNSID_EMPTY = 2000013;</code>
     */
    ERROR_SNSID_EMPTY(20, 2000013),
    /**
     * <code>ERROR_EMAIL_REGISTERED = 2000014;</code>
     */
    ERROR_EMAIL_REGISTERED(21, 2000014),
    /**
     * <code>ERROR_PASSWORD_INVALID = 2000015;</code>
     */
    ERROR_PASSWORD_INVALID(22, 2000015),
    /**
     * <code>ERROR_SNS_AUTH_FAIL = 2000016;</code>
     */
    ERROR_SNS_AUTH_FAIL(23, 2000016),
    /**
     * <code>ERROR_SNS_AUTH_CANCEL = 2000017;</code>
     */
    ERROR_SNS_AUTH_CANCEL(24, 2000017),
    /**
     * <code>ERROR_SNS_AUTH_ERROR_UNKNOWN = 2000018;</code>
     */
    ERROR_SNS_AUTH_ERROR_UNKNOWN(25, 2000018),
    /**
     * <code>ERROR_SNS_GET_USER_INFO = 2000019;</code>
     */
    ERROR_SNS_GET_USER_INFO(26, 2000019),
    /**
     * <code>ERROR_INVITE_CODE_NULL = 2000020;</code>
     */
    ERROR_INVITE_CODE_NULL(27, 2000020),
    /**
     * <code>ERROR_NO_INVITE_CODE_AVAILABLE = 2000021;</code>
     */
    ERROR_NO_INVITE_CODE_AVAILABLE(28, 2000021),
    /**
     * <code>ERROR_USER_TAG_LIST_NULL = 2000022;</code>
     */
    ERROR_USER_TAG_LIST_NULL(29, 2000022),
    /**
     * <code>ERROR_USER_TAG_NAME_DUPLICATE = 2000023;</code>
     */
    ERROR_USER_TAG_NAME_DUPLICATE(30, 2000023),
    /**
     * <code>ERROR_FEED_ACTION_INVALID = 3000001;</code>
     *
     * <pre>
     * feed
     * </pre>
     */
    ERROR_FEED_ACTION_INVALID(31, 3000001),
    /**
     * <code>ERROR_CREATE_IMAGE = 3000002;</code>
     */
    ERROR_CREATE_IMAGE(32, 3000002),
    /**
     * <code>ERROR_UPLOAD_IMAGE = 3000003;</code>
     */
    ERROR_UPLOAD_IMAGE(33, 3000003),
    ;

    /**
     * <code>ERROR_READ_POST_DATA = 1000001;</code>
     *
     * <pre>
     * system
     * </pre>
     */
    public static final int ERROR_READ_POST_DATA_VALUE = 1000001;
    /**
     * <code>ERROR_PARSE_POST_DATA = 1000002;</code>
     */
    public static final int ERROR_PARSE_POST_DATA_VALUE = 1000002;
    /**
     * <code>ERROR_NO_SERVICE_FOR_TYPE = 1000003;</code>
     */
    public static final int ERROR_NO_SERVICE_FOR_TYPE_VALUE = 1000003;
    /**
     * <code>ERROR_SERVICE_CATCH_EXCEPTION = 1000004;</code>
     */
    public static final int ERROR_SERVICE_CATCH_EXCEPTION_VALUE = 1000004;
    /**
     * <code>ERROR_UNKNOWN = 1000005;</code>
     */
    public static final int ERROR_UNKNOWN_VALUE = 1000005;
    /**
     * <code>ERROR_DATA_RESPONSE_NULL = 1000006;</code>
     */
    public static final int ERROR_DATA_RESPONSE_NULL_VALUE = 1000006;
    /**
     * <code>ERROR_JSON_EXCEPTION = 1000007;</code>
     */
    public static final int ERROR_JSON_EXCEPTION_VALUE = 1000007;
    /**
     * <code>ERROR_INCORRECT_INPUT_DATA = 1000008;</code>
     */
    public static final int ERROR_INCORRECT_INPUT_DATA_VALUE = 1000008;
    /**
     * <code>ERROR_USER_LOGIN_UNKNOWN_TYPE = 2000001;</code>
     *
     * <pre>
     * user
     * </pre>
     */
    public static final int ERROR_USER_LOGIN_UNKNOWN_TYPE_VALUE = 2000001;
    /**
     * <code>ERROR_USER_LOGIN_INFO_EMPTY = 2000002;</code>
     */
    public static final int ERROR_USER_LOGIN_INFO_EMPTY_VALUE = 2000002;
    /**
     * <code>ERROR_USER_REGISTER_UNKNOWN_TYPE = 2000003;</code>
     */
    public static final int ERROR_USER_REGISTER_UNKNOWN_TYPE_VALUE = 2000003;
    /**
     * <code>ERROR_USER_REGISTER_INFO_EMPTY = 2000004;</code>
     */
    public static final int ERROR_USER_REGISTER_INFO_EMPTY_VALUE = 2000004;
    /**
     * <code>ERROR_USER_REGISTER_INVALID_INVITE_CODE = 2000005;</code>
     */
    public static final int ERROR_USER_REGISTER_INVALID_INVITE_CODE_VALUE = 2000005;
    /**
     * <code>ERROR_USER_NOT_FOUND = 2000006;</code>
     */
    public static final int ERROR_USER_NOT_FOUND_VALUE = 2000006;
    /**
     * <code>ERROR_FRIEND_NOT_FOUND = 2000007;</code>
     */
    public static final int ERROR_FRIEND_NOT_FOUND_VALUE = 2000007;
    /**
     * <code>ERROR_FRIEND_NOT_ALLOW_ADD_ME = 2000008;</code>
     */
    public static final int ERROR_FRIEND_NOT_ALLOW_ADD_ME_VALUE = 2000008;
    /**
     * <code>ERROR_INVITE_CODE_NOT_EXIST = 2000009;</code>
     */
    public static final int ERROR_INVITE_CODE_NOT_EXIST_VALUE = 2000009;
    /**
     * <code>ERROR_INVITE_CODE_USED = 2000010;</code>
     */
    public static final int ERROR_INVITE_CODE_USED_VALUE = 2000010;
    /**
     * <code>ERROR_EMAIL_EMPTY = 2000011;</code>
     */
    public static final int ERROR_EMAIL_EMPTY_VALUE = 2000011;
    /**
     * <code>ERROR_MOBILE_EMPTY = 2000012;</code>
     */
    public static final int ERROR_MOBILE_EMPTY_VALUE = 2000012;
    /**
     * <code>ERROR_SNSID_EMPTY = 2000013;</code>
     */
    public static final int ERROR_SNSID_EMPTY_VALUE = 2000013;
    /**
     * <code>ERROR_EMAIL_REGISTERED = 2000014;</code>
     */
    public static final int ERROR_EMAIL_REGISTERED_VALUE = 2000014;
    /**
     * <code>ERROR_PASSWORD_INVALID = 2000015;</code>
     */
    public static final int ERROR_PASSWORD_INVALID_VALUE = 2000015;
    /**
     * <code>ERROR_SNS_AUTH_FAIL = 2000016;</code>
     */
    public static final int ERROR_SNS_AUTH_FAIL_VALUE = 2000016;
    /**
     * <code>ERROR_SNS_AUTH_CANCEL = 2000017;</code>
     */
    public static final int ERROR_SNS_AUTH_CANCEL_VALUE = 2000017;
    /**
     * <code>ERROR_SNS_AUTH_ERROR_UNKNOWN = 2000018;</code>
     */
    public static final int ERROR_SNS_AUTH_ERROR_UNKNOWN_VALUE = 2000018;
    /**
     * <code>ERROR_SNS_GET_USER_INFO = 2000019;</code>
     */
    public static final int ERROR_SNS_GET_USER_INFO_VALUE = 2000019;
    /**
     * <code>ERROR_INVITE_CODE_NULL = 2000020;</code>
     */
    public static final int ERROR_INVITE_CODE_NULL_VALUE = 2000020;
    /**
     * <code>ERROR_NO_INVITE_CODE_AVAILABLE = 2000021;</code>
     */
    public static final int ERROR_NO_INVITE_CODE_AVAILABLE_VALUE = 2000021;
    /**
     * <code>ERROR_USER_TAG_LIST_NULL = 2000022;</code>
     */
    public static final int ERROR_USER_TAG_LIST_NULL_VALUE = 2000022;
    /**
     * <code>ERROR_USER_TAG_NAME_DUPLICATE = 2000023;</code>
     */
    public static final int ERROR_USER_TAG_NAME_DUPLICATE_VALUE = 2000023;
    /**
     * <code>ERROR_FEED_ACTION_INVALID = 3000001;</code>
     *
     * <pre>
     * feed
     * </pre>
     */
    public static final int ERROR_FEED_ACTION_INVALID_VALUE = 3000001;
    /**
     * <code>ERROR_CREATE_IMAGE = 3000002;</code>
     */
    public static final int ERROR_CREATE_IMAGE_VALUE = 3000002;
    /**
     * <code>ERROR_UPLOAD_IMAGE = 3000003;</code>
     */
    public static final int ERROR_UPLOAD_IMAGE_VALUE = 3000003;


    public final int getNumber() { return value; }

    public static PBError valueOf(int value) {
      switch (value) {
        case 1000001: return ERROR_READ_POST_DATA;
        case 1000002: return ERROR_PARSE_POST_DATA;
        case 1000003: return ERROR_NO_SERVICE_FOR_TYPE;
        case 1000004: return ERROR_SERVICE_CATCH_EXCEPTION;
        case 1000005: return ERROR_UNKNOWN;
        case 1000006: return ERROR_DATA_RESPONSE_NULL;
        case 1000007: return ERROR_JSON_EXCEPTION;
        case 1000008: return ERROR_INCORRECT_INPUT_DATA;
        case 2000001: return ERROR_USER_LOGIN_UNKNOWN_TYPE;
        case 2000002: return ERROR_USER_LOGIN_INFO_EMPTY;
        case 2000003: return ERROR_USER_REGISTER_UNKNOWN_TYPE;
        case 2000004: return ERROR_USER_REGISTER_INFO_EMPTY;
        case 2000005: return ERROR_USER_REGISTER_INVALID_INVITE_CODE;
        case 2000006: return ERROR_USER_NOT_FOUND;
        case 2000007: return ERROR_FRIEND_NOT_FOUND;
        case 2000008: return ERROR_FRIEND_NOT_ALLOW_ADD_ME;
        case 2000009: return ERROR_INVITE_CODE_NOT_EXIST;
        case 2000010: return ERROR_INVITE_CODE_USED;
        case 2000011: return ERROR_EMAIL_EMPTY;
        case 2000012: return ERROR_MOBILE_EMPTY;
        case 2000013: return ERROR_SNSID_EMPTY;
        case 2000014: return ERROR_EMAIL_REGISTERED;
        case 2000015: return ERROR_PASSWORD_INVALID;
        case 2000016: return ERROR_SNS_AUTH_FAIL;
        case 2000017: return ERROR_SNS_AUTH_CANCEL;
        case 2000018: return ERROR_SNS_AUTH_ERROR_UNKNOWN;
        case 2000019: return ERROR_SNS_GET_USER_INFO;
        case 2000020: return ERROR_INVITE_CODE_NULL;
        case 2000021: return ERROR_NO_INVITE_CODE_AVAILABLE;
        case 2000022: return ERROR_USER_TAG_LIST_NULL;
        case 2000023: return ERROR_USER_TAG_NAME_DUPLICATE;
        case 3000001: return ERROR_FEED_ACTION_INVALID;
        case 3000002: return ERROR_CREATE_IMAGE;
        case 3000003: return ERROR_UPLOAD_IMAGE;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<PBError>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<PBError>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<PBError>() {
            public PBError findValueByNumber(int number) {
              return PBError.valueOf(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.orange.protocol.message.ErrorProtos.getDescriptor().getEnumTypes().get(0);
    }

    private static final PBError[] VALUES = values();

    public static PBError valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int index;
    private final int value;

    private PBError(int index, int value) {
      this.index = index;
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:barrage.PBError)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013Error.proto\022\007barrage*\304\010\n\007PBError\022\032\n\024ER" +
      "ROR_READ_POST_DATA\020\301\204=\022\033\n\025ERROR_PARSE_PO" +
      "ST_DATA\020\302\204=\022\037\n\031ERROR_NO_SERVICE_FOR_TYPE" +
      "\020\303\204=\022#\n\035ERROR_SERVICE_CATCH_EXCEPTION\020\304\204" +
      "=\022\023\n\rERROR_UNKNOWN\020\305\204=\022\036\n\030ERROR_DATA_RES" +
      "PONSE_NULL\020\306\204=\022\032\n\024ERROR_JSON_EXCEPTION\020\307" +
      "\204=\022 \n\032ERROR_INCORRECT_INPUT_DATA\020\310\204=\022#\n\035" +
      "ERROR_USER_LOGIN_UNKNOWN_TYPE\020\201\211z\022!\n\033ERR" +
      "OR_USER_LOGIN_INFO_EMPTY\020\202\211z\022&\n ERROR_US" +
      "ER_REGISTER_UNKNOWN_TYPE\020\203\211z\022$\n\036ERROR_US",
      "ER_REGISTER_INFO_EMPTY\020\204\211z\022-\n\'ERROR_USER" +
      "_REGISTER_INVALID_INVITE_CODE\020\205\211z\022\032\n\024ERR" +
      "OR_USER_NOT_FOUND\020\206\211z\022\034\n\026ERROR_FRIEND_NO" +
      "T_FOUND\020\207\211z\022#\n\035ERROR_FRIEND_NOT_ALLOW_AD" +
      "D_ME\020\210\211z\022!\n\033ERROR_INVITE_CODE_NOT_EXIST\020" +
      "\211\211z\022\034\n\026ERROR_INVITE_CODE_USED\020\212\211z\022\027\n\021ERR" +
      "OR_EMAIL_EMPTY\020\213\211z\022\030\n\022ERROR_MOBILE_EMPTY" +
      "\020\214\211z\022\027\n\021ERROR_SNSID_EMPTY\020\215\211z\022\034\n\026ERROR_E" +
      "MAIL_REGISTERED\020\216\211z\022\034\n\026ERROR_PASSWORD_IN" +
      "VALID\020\217\211z\022\031\n\023ERROR_SNS_AUTH_FAIL\020\220\211z\022\033\n\025",
      "ERROR_SNS_AUTH_CANCEL\020\221\211z\022\"\n\034ERROR_SNS_A" +
      "UTH_ERROR_UNKNOWN\020\222\211z\022\035\n\027ERROR_SNS_GET_U" +
      "SER_INFO\020\223\211z\022\034\n\026ERROR_INVITE_CODE_NULL\020\224" +
      "\211z\022$\n\036ERROR_NO_INVITE_CODE_AVAILABLE\020\225\211z" +
      "\022\036\n\030ERROR_USER_TAG_LIST_NULL\020\226\211z\022#\n\035ERRO" +
      "R_USER_TAG_NAME_DUPLICATE\020\227\211z\022 \n\031ERROR_F" +
      "EED_ACTION_INVALID\020\301\215\267\001\022\031\n\022ERROR_CREATE_" +
      "IMAGE\020\302\215\267\001\022\031\n\022ERROR_UPLOAD_IMAGE\020\303\215\267\001B*\n" +
      "\033com.orange.protocol.messageB\013ErrorProto" +
      "s"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
