export type AmplifyDependentResourcesAttributes = {
    "auth": {
        "needtofeedae53688e": {
            "IdentityPoolId": "string",
            "IdentityPoolName": "string",
            "HostedUIDomain": "string",
            "OAuthMetadata": "string",
            "UserPoolId": "string",
            "UserPoolArn": "string",
            "UserPoolName": "string",
            "AppClientIDWeb": "string",
            "AppClientID": "string",
            "CreatedSNSRole": "string",
            "GoogleWebClient": "string",
            "GoogleAndroidClient": "string",
            "FacebookWebClient": "string"
        },
        "userPoolGroups": {
            "needtofeedUserpoolGroupGroupRole": "string"
        }
    },
    "storage": {
        "s3475e3a0f": {
            "BucketName": "string",
            "Region": "string"
        }
    },
    "api": {
        "restaurants": {
            "GraphQLAPIIdOutput": "string",
            "GraphQLAPIEndpointOutput": "string"
        }
    },
    "function": {
        "needtofeedae53688eCustomMessage": {
            "Name": "string",
            "Arn": "string",
            "LambdaExecutionRole": "string",
            "Region": "string"
        }
    }
}